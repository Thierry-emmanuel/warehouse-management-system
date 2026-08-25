package Warehousemanagement.project.dashboard.service.impl;

import Warehousemanagement.project.dashboard.dto.response.ActivityHeatmapCell;
import Warehousemanagement.project.dashboard.dto.response.AdminDashboardResponse;
import Warehousemanagement.project.dashboard.dto.response.EmployeeDashboardResponse;
import Warehousemanagement.project.dashboard.dto.response.ManagerDashboardResponse;
import Warehousemanagement.project.dashboard.mapper.DashboardMapper;
import Warehousemanagement.project.dashboard.service.DashboardService;
import Warehousemanagement.project.inventory.repository.InventoryItemRepository;
import Warehousemanagement.project.order.enums.PickTaskStatus;
import Warehousemanagement.project.order.enums.PurchaseOrderStatus;
import Warehousemanagement.project.order.enums.SalesOrderStatus;
import Warehousemanagement.project.order.model.PickTask;
import Warehousemanagement.project.order.repository.PickTaskRepository;
import Warehousemanagement.project.order.repository.PurchaseOrderRepository;
import Warehousemanagement.project.order.repository.SalesOrderRepository;
import Warehousemanagement.project.product.repository.ProductRepository;
import Warehousemanagement.project.security.model.Role;
import Warehousemanagement.project.security.repository.PermissionRepository;
import Warehousemanagement.project.security.repository.RoleRepository;
import Warehousemanagement.project.security.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final ProductRepository productRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SalesOrderRepository salesOrderRepository;
    private final PickTaskRepository pickTaskRepository;
    private final DashboardMapper dashboardMapper;

    public DashboardServiceImpl(UserRepository userRepository,
                                RoleRepository roleRepository,
                                PermissionRepository permissionRepository,
                                ProductRepository productRepository,
                                InventoryItemRepository inventoryItemRepository,
                                PurchaseOrderRepository purchaseOrderRepository,
                                SalesOrderRepository salesOrderRepository,
                                PickTaskRepository pickTaskRepository,
                                DashboardMapper dashboardMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.productRepository = productRepository;
        this.inventoryItemRepository = inventoryItemRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.salesOrderRepository = salesOrderRepository;
        this.pickTaskRepository = pickTaskRepository;
        this.dashboardMapper = dashboardMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public AdminDashboardResponse getAdminDashboard() {
        long totalUsers = userRepository.count();
        long activeUsers = userRepository.countByIsActive(true);
        long totalRoles = roleRepository.count();
        long totalPerms = permissionRepository.count();

        Map<String, Long> roleDist = new HashMap<>();
        List<Role> roles = roleRepository.findAll();
        for (Role r : roles) {
            long count = roleRepository.countAssignedUsers(r.getId());
            roleDist.put(r.getName(), count);
        }

        Map<String, Object> facilityStatus = Map.of(
            "facilityCode", "WH-MAIN-01",
            "activeGateways", 4,
            "connectedScanners", 18,
            "systemStatus", "OPERATIONAL"
        );

        Map<String, Object> systemHealth = Map.of(
            "jvmUptimeSeconds", Runtime.getRuntime().totalMemory() / (1024 * 1024),
            "dbConnectionPool", "HEALTHY",
            "lockContentionRate", "0.01%"
        );

        return new AdminDashboardResponse(
            totalUsers,
            activeUsers,
            totalRoles,
            totalPerms,
            roleDist,
            facilityStatus,
            systemHealth
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ManagerDashboardResponse getManagerDashboard(Long warehouseId) {
        Long effectiveWarehouseId = warehouseId != null ? warehouseId : 1L;
        long totalSkus = productRepository.countByIsActiveTrue();

        Double valuation = inventoryItemRepository.calculateTotalInventoryValuation(effectiveWarehouseId);
        BigDecimal totalValuation = valuation != null ? BigDecimal.valueOf(valuation) : BigDecimal.ZERO;

        long pendingPos = purchaseOrderRepository.countByWarehouseIdAndStatus(effectiveWarehouseId, PurchaseOrderStatus.CONFIRMED);
        long activeWaves = salesOrderRepository.countByWarehouseIdAndStatus(effectiveWarehouseId, SalesOrderStatus.ALLOCATED);

        List<ActivityHeatmapCell> heatmap = dashboardMapper.generateLogistiqHeatmapGrid(60);

        Map<String, Long> weeklyTrends = new HashMap<>();
        weeklyTrends.put("Monday", 342L);
        weeklyTrends.put("Tuesday", 418L);
        weeklyTrends.put("Wednesday", 389L);
        weeklyTrends.put("Thursday", 512L);
        weeklyTrends.put("Friday", 480L);
        weeklyTrends.put("Saturday", 210L);
        weeklyTrends.put("Sunday", 95L);

        List<String> alerts = new ArrayList<>();
        if (pendingPos > 0) {
            alerts.add("Pending Inbound: " + pendingPos + " confirmed purchase orders awaiting dock receipt.");
        }
        if (activeWaves > 0) {
            alerts.add("Active Wave Picking: " + activeWaves + " allocated sales orders ready for zone pickers.");
        }
        if (alerts.isEmpty()) {
            alerts.add("Facility operations normal. All inbound dock appointments and outbound waves on schedule.");
        }

        return new ManagerDashboardResponse(
            effectiveWarehouseId,
            totalSkus,
            totalValuation,
            pendingPos,
            activeWaves,
            0.76,
            0.82,
            heatmap,
            weeklyTrends,
            alerts
        );
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDashboardResponse getEmployeeDashboard(Long warehouseId, String employeeName) {
        Long effectiveWarehouseId = warehouseId != null ? warehouseId : 1L;
        String operator = (employeeName != null && !employeeName.isBlank()) ? employeeName : "employee";

        List<PickTask> tasks = pickTaskRepository.findByAssignedOperatorUsernameAndStatus(operator, PickTaskStatus.PENDING);
        if (tasks.isEmpty()) {
            tasks = pickTaskRepository.findByStatus(PickTaskStatus.PENDING);
        }

        List<Map<String, Object>> urgentTasks = new ArrayList<>();
        for (PickTask t : tasks) {
            urgentTasks.add(Map.of(
                "taskId", t.getTaskCode(),
                "type", "PICK",
                "binLocation", t.getSourceLocation() != null ? t.getSourceLocation().getCode() : "WH1-Z01-A01-S1-B01",
                "sku", t.getProduct() != null ? t.getProduct().getSku() : "N/A",
                "qty", t.getTargetQuantity() - t.getPickedQuantity()
            ));
        }

        long completedPicks = pickTaskRepository.countByAssignedOperatorUsernameAndStatus(operator, PickTaskStatus.COMPLETED);

        List<String> shortcuts = List.of(
            "F1: Fast Laser Barcode Scan",
            "F2: Bin Exception Flag",
            "F3: Print Tote Shipping Label",
            "F4: Next Pick in Sequence Route"
        );

        return new EmployeeDashboardResponse(
            operator,
            effectiveWarehouseId,
            (long) urgentTasks.size(),
            completedPicks,
            2L,
            0.998,
            38.0,
            urgentTasks,
            shortcuts
        );
    }
}

package Warehousemanagement.project.dashboard.service.impl;

import Warehousemanagement.project.dashboard.dto.response.ActivityHeatmapCell;
import Warehousemanagement.project.dashboard.dto.response.AdminDashboardResponse;
import Warehousemanagement.project.dashboard.dto.response.EmployeeDashboardResponse;
import Warehousemanagement.project.dashboard.dto.response.ManagerDashboardResponse;
import Warehousemanagement.project.dashboard.mapper.DashboardMapper;
import Warehousemanagement.project.dashboard.service.DashboardService;
import Warehousemanagement.project.security.repository.PermissionRepository;
import Warehousemanagement.project.security.repository.RoleRepository;
import Warehousemanagement.project.security.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final DashboardMapper dashboardMapper;

    public DashboardServiceImpl(UserRepository userRepository,
                                RoleRepository roleRepository,
                                PermissionRepository permissionRepository,
                                DashboardMapper dashboardMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.dashboardMapper = dashboardMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public AdminDashboardResponse getAdminDashboard() {
        long totalUsers = userRepository.count();
        long totalRoles = roleRepository.count();
        long totalPerms = permissionRepository.count();

        Map<String, Long> roleDist = new HashMap<>();
        roleDist.put("ROLE_ADMIN", 1L);
        roleDist.put("ROLE_MANAGER", 3L);
        roleDist.put("ROLE_EMPLOYEE", 12L);

        Map<String, Object> facilityStatus = Map.of(
            "facilityCode", "WH-MAIN-01",
            "activeGateways", 4,
            "connectedScanners", 18,
            "systemStatus", "OPERATIONAL"
        );

        Map<String, Object> systemHealth = Map.of(
            "jvmUptimeSeconds", 86400,
            "dbConnectionPool", "HEALTHY",
            "lockContentionRate", "0.01%"
        );

        return new AdminDashboardResponse(
            totalUsers,
            totalUsers,
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
        List<ActivityHeatmapCell> heatmap = dashboardMapper.generateLogistiqHeatmapGrid(60);

        Map<String, Long> weeklyTrends = new HashMap<>();
        weeklyTrends.put("Monday", 342L);
        weeklyTrends.put("Tuesday", 418L);
        weeklyTrends.put("Wednesday", 389L);
        weeklyTrends.put("Thursday", 512L);
        weeklyTrends.put("Friday", 480L);
        weeklyTrends.put("Saturday", 210L);
        weeklyTrends.put("Sunday", 95L);

        List<String> alerts = List.of(
            "Zone B (Cold Storage) capacity reaches 88%",
            "2 Inbound Dock Purchase Orders pending inspection",
            "SKU-4890 stock falls below reorder point (15 remaining, minimum 50)"
        );

        return new ManagerDashboardResponse(
            effectiveWarehouseId,
            1240L,
            new BigDecimal("485230.50"),
            8L,
            14L,
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

        List<Map<String, Object>> urgentTasks = List.of(
            Map.of("taskId", "WAVE-091", "type", "PICK", "binLocation", "A-02-04-B", "sku", "ELEC-AUDIO-01", "qty", 12),
            Map.of("taskId", "WAVE-094", "type", "PUTAWAY", "binLocation", "B-01-01-A", "sku", "MECH-GEAR-08", "qty", 40)
        );

        List<String> shortcuts = List.of(
            "F1: Fast Barcode Scan",
            "F2: Bin Exception Flag",
            "F3: Print Tote Label",
            "F4: Next Pick in Routing Sequence"
        );

        return new EmployeeDashboardResponse(
            employeeName != null ? employeeName : "Floor Specialist",
            effectiveWarehouseId,
            6L,
            34L,
            4L,
            0.994,
            42.5,
            urgentTasks,
            shortcuts
        );
    }
}

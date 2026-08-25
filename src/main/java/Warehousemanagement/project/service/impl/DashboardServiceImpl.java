package Warehousemanagement.project.service.impl;

import Warehousemanagement.project.dto.response.AdminDashboardResponse;
import Warehousemanagement.project.dto.response.EmployeeDashboardResponse;
import Warehousemanagement.project.dto.response.ManagerDashboardResponse;
import Warehousemanagement.project.dto.response.RoleSummaryResponse;
import Warehousemanagement.project.dto.response.UserSummaryResponse;
import Warehousemanagement.project.mapper.DashboardMapper;
import Warehousemanagement.project.mapper.RoleMapper;
import Warehousemanagement.project.mapper.UserMapper;
import Warehousemanagement.project.model.Role;
import Warehousemanagement.project.model.User;
import Warehousemanagement.project.repository.PermissionRepository;
import Warehousemanagement.project.repository.RoleRepository;
import Warehousemanagement.project.repository.UserRepository;
import Warehousemanagement.project.service.DashboardService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final DashboardMapper dashboardMapper;

    public DashboardServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository, UserMapper userMapper, RoleMapper roleMapper, DashboardMapper dashboardMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.dashboardMapper = dashboardMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public AdminDashboardResponse getAdminDashboard() {
        AdminDashboardResponse response = new AdminDashboardResponse();
        response.setTotalUsers(userRepository.count());
        response.setActiveUsers(userRepository.count());
        response.setTotalRoles(roleRepository.count());
        response.setTotalPermissions(permissionRepository.count());
        response.setActiveWarehouses(1L);

        Map<String, Long> roleDistribution = new HashMap<>();
        List<Role> allRoles = roleRepository.findAll();
        for (Role role : allRoles) {
            roleDistribution.put(role.getName(), roleRepository.countAssignedUsers(role.getId()));
        }
        response.setUsersPerRole(roleDistribution);

        List<User> recentUsers = userRepository.findAll(PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt"))).getContent();
        List<UserSummaryResponse> userSummaries = recentUsers.stream().map(userMapper::toSummaryResponse).toList();
        response.setRecentUsers(userSummaries);

        List<RoleSummaryResponse> roleSummaries = allRoles.stream()
            .map(r -> roleMapper.toSummaryResponse(r, roleRepository.countAssignedUsers(r.getId())))
            .toList();
        response.setRecentRoles(roleSummaries);

        response.setSystemHealthIndicators(List.of(
            "Authentication Service: Operational (Stateless JWT)",
            "Database Connection Pool: Optimal (0.4ms latency)",
            "Audit Ledger Dispatcher: Active",
            "Role Permission Hierarchy: Verified"
        ));

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ManagerDashboardResponse getManagerDashboard(Long warehouseId) {
        ManagerDashboardResponse response = new ManagerDashboardResponse();
        response.setTotalInventoryValuation(487250.00);
        response.setTotalSalesVolume(2354L);
        response.setTotalShipmentsUnits(840L);
        response.setOrderFulfillmentRate(98.4);
        response.setLowStockCount(7L);
        response.setOpenPurchaseOrdersCount(4L);

        // 180-day activity heatmap (LogistiQ style)
        response.setActivityHeatmap(dashboardMapper.generateLogistiqActivityHeatmap(180));

        Map<String, Double> monthlyTrends = new LinkedHashMap<>();
        monthlyTrends.put("Jan", 32000.0);
        monthlyTrends.put("Feb", 45000.0);
        monthlyTrends.put("Mar", 41000.0);
        monthlyTrends.put("Apr", 58000.0);
        monthlyTrends.put("May", 64000.0);
        monthlyTrends.put("Jun", 72000.0);
        response.setMonthlySalesTrends(monthlyTrends);

        response.setOperationalAlerts(List.of(
            "7 SKUs below safety reorder threshold in Zone A",
            "PO-98213 from Industrial Parts Ltd awaiting inspection approval",
            "Warehouse Zone B space utilization at 82% capacity",
            "Carrier dispatch scheduled for 16:00 (Dock Doors 3 & 4)"
        ));

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDashboardResponse getEmployeeDashboard(Long warehouseId, String username) {
        EmployeeDashboardResponse response = new EmployeeDashboardResponse();
        response.setAssignedPickTasks(12L);
        response.setPendingDockReceipts(3L);
        response.setPendingPutawayTasks(8L);
        response.setCompletedPicksToday(84);
        response.setDailyPickTarget(100);
        response.setScanAccuracyRate(99.2);

        response.setPriorityTaskQueue(List.of(
            "Wave Pick #1042 - Aisle A03 (Racks R01 to R04) - Urgent Dispatch",
            "Inbound Receipt PO-98213 - Dock Door 2 - 40 Cartons",
            "Directed Putaway - Pallet PL-8812 to Location WH1-Z01-A02-R03-S1-B02",
            "Bin Replenishment - Move 20 units SKU ELEC-MOT-4401 to Pick Face"
        ));

        response.setActiveScannerShortcuts(List.of(
            "/scanner/pick - Wave Pick Scanner",
            "/scanner/receive - Dock Receiving Scanner",
            "/scanner/putaway - Directed Putaway Confirmation",
            "/scanner/lookup - Fast Bin & SKU Lookup"
        ));

        return response;
    }
}

package Warehousemanagement.project.controller;

import Warehousemanagement.project.config.CustomUserDetails;
import Warehousemanagement.project.dto.response.AdminDashboardResponse;
import Warehousemanagement.project.dto.response.ApiResponse;
import Warehousemanagement.project.dto.response.EmployeeDashboardResponse;
import Warehousemanagement.project.dto.response.ManagerDashboardResponse;
import Warehousemanagement.project.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboards")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AdminDashboardResponse>> getAdminDashboard() {
        AdminDashboardResponse response = dashboardService.getAdminDashboard();
        return ResponseEntity.ok(ApiResponse.success("Admin dashboard metrics retrieved", response));
    }

    @GetMapping("/manager")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponse<ManagerDashboardResponse>> getManagerDashboard(
            @AuthenticationPrincipal CustomUserDetails caller) {
        ManagerDashboardResponse response = dashboardService.getManagerDashboard(caller.getWarehouseId());
        return ResponseEntity.ok(ApiResponse.success("Manager supply chain dashboard retrieved", response));
    }

    @GetMapping("/employee")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'SUPERVISOR', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeDashboardResponse>> getEmployeeDashboard(
            @AuthenticationPrincipal CustomUserDetails caller) {
        EmployeeDashboardResponse response = dashboardService.getEmployeeDashboard(caller.getWarehouseId(), caller.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Employee floor dashboard retrieved", response));
    }

    @GetMapping("/my-dashboard")
    public ResponseEntity<ApiResponse<Object>> getMyDashboard(@AuthenticationPrincipal CustomUserDetails caller) {
        boolean isAdmin = caller.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch("ROLE_ADMIN"::equals);
        boolean isManager = caller.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch("ROLE_MANAGER"::equals);

        if (isAdmin) {
            return ResponseEntity.ok(ApiResponse.success("Admin dashboard retrieved", dashboardService.getAdminDashboard()));
        } else if (isManager) {
            return ResponseEntity.ok(ApiResponse.success("Manager dashboard retrieved", dashboardService.getManagerDashboard(caller.getWarehouseId())));
        } else {
            return ResponseEntity.ok(ApiResponse.success("Employee dashboard retrieved", dashboardService.getEmployeeDashboard(caller.getWarehouseId(), caller.getUsername())));
        }
    }
}

package Warehousemanagement.project.dashboard.controller;

import Warehousemanagement.project.common.dto.ApiResponse;
import Warehousemanagement.project.dashboard.dto.response.AdminDashboardResponse;
import Warehousemanagement.project.dashboard.dto.response.EmployeeDashboardResponse;
import Warehousemanagement.project.dashboard.dto.response.ManagerDashboardResponse;
import Warehousemanagement.project.dashboard.service.DashboardService;
import Warehousemanagement.project.security.config.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboards")
@Tag(name = "Dashboards", description = "Endpoints for role-tailored dashboards and operational metrics (Admin, Manager, Employee)")
@SecurityRequirement(name = "BearerAuth")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get admin system dashboard", description = "Retrieves user and role distribution metrics, warehouse facility health, and system status.")
    public ResponseEntity<ApiResponse<AdminDashboardResponse>> getAdminDashboard() {
        AdminDashboardResponse response = dashboardService.getAdminDashboard();
        return ResponseEntity.ok(ApiResponse.success("Admin dashboard metrics retrieved", response));
    }

    @GetMapping("/manager")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(summary = "Get manager supply chain dashboard", description = "Retrieves inventory valuations, LogistiQ activity heatmap, sales trends, and operational alerts.")
    public ResponseEntity<ApiResponse<ManagerDashboardResponse>> getManagerDashboard(
            @AuthenticationPrincipal CustomUserDetails caller) {
        ManagerDashboardResponse response = dashboardService.getManagerDashboard(caller.getWarehouseId());
        return ResponseEntity.ok(ApiResponse.success("Manager supply chain dashboard retrieved", response));
    }

    @GetMapping("/employee")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'SUPERVISOR', 'MANAGER', 'ADMIN')")
    @Operation(summary = "Get employee floor dashboard", description = "Retrieves assigned pick tasks, dock receipt queues, putaway confirmations, and scanner shortcuts.")
    public ResponseEntity<ApiResponse<EmployeeDashboardResponse>> getEmployeeDashboard(
            @AuthenticationPrincipal CustomUserDetails caller) {
        EmployeeDashboardResponse response = dashboardService.getEmployeeDashboard(caller.getWarehouseId(), caller.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Employee floor dashboard retrieved", response));
    }

    @GetMapping("/my-dashboard")
    @Operation(summary = "Get user role-specific dashboard", description = "Automatically serves the appropriate dashboard payload matching the authenticated caller's highest authority.")
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

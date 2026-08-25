package Warehousemanagement.project.service;

import Warehousemanagement.project.dto.response.AdminDashboardResponse;
import Warehousemanagement.project.dto.response.EmployeeDashboardResponse;
import Warehousemanagement.project.dto.response.ManagerDashboardResponse;

public interface DashboardService {

    AdminDashboardResponse getAdminDashboard();

    ManagerDashboardResponse getManagerDashboard(Long warehouseId);

    EmployeeDashboardResponse getEmployeeDashboard(Long warehouseId, String username);
}

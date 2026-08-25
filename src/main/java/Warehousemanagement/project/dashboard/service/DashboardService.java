package Warehousemanagement.project.dashboard.service;

import Warehousemanagement.project.dashboard.dto.response.AdminDashboardResponse;
import Warehousemanagement.project.dashboard.dto.response.EmployeeDashboardResponse;
import Warehousemanagement.project.dashboard.dto.response.ManagerDashboardResponse;

public interface DashboardService {

    AdminDashboardResponse getAdminDashboard();

    ManagerDashboardResponse getManagerDashboard(Long warehouseId);

    EmployeeDashboardResponse getEmployeeDashboard(Long warehouseId, String employeeName);
}

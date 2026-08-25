package Warehousemanagement.project.dto.response;

import java.util.List;
import java.util.Map;

public class AdminDashboardResponse {

    private long totalUsers;
    private long activeUsers;
    private long totalRoles;
    private long totalPermissions;
    private long activeWarehouses;
    private Map<String, Long> usersPerRole;
    private List<UserSummaryResponse> recentUsers;
    private List<RoleSummaryResponse> recentRoles;
    private List<String> systemHealthIndicators;

    public AdminDashboardResponse() {
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(long activeUsers) {
        this.activeUsers = activeUsers;
    }

    public long getTotalRoles() {
        return totalRoles;
    }

    public void setTotalRoles(long totalRoles) {
        this.totalRoles = totalRoles;
    }

    public long getTotalPermissions() {
        return totalPermissions;
    }

    public void setTotalPermissions(long totalPermissions) {
        this.totalPermissions = totalPermissions;
    }

    public long getActiveWarehouses() {
        return activeWarehouses;
    }

    public void setActiveWarehouses(long activeWarehouses) {
        this.activeWarehouses = activeWarehouses;
    }

    public Map<String, Long> getUsersPerRole() {
        return usersPerRole;
    }

    public void setUsersPerRole(Map<String, Long> usersPerRole) {
        this.usersPerRole = usersPerRole;
    }

    public List<UserSummaryResponse> getRecentUsers() {
        return recentUsers;
    }

    public void setRecentUsers(List<UserSummaryResponse> recentUsers) {
        this.recentUsers = recentUsers;
    }

    public List<RoleSummaryResponse> getRecentRoles() {
        return recentRoles;
    }

    public void setRecentRoles(List<RoleSummaryResponse> recentRoles) {
        this.recentRoles = recentRoles;
    }

    public List<String> getSystemHealthIndicators() {
        return systemHealthIndicators;
    }

    public void setSystemHealthIndicators(List<String> systemHealthIndicators) {
        this.systemHealthIndicators = systemHealthIndicators;
    }
}

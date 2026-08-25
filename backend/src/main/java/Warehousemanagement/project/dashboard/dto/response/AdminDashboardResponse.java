package Warehousemanagement.project.dashboard.dto.response;

import java.util.Map;

public class AdminDashboardResponse {

    private long totalUsers;
    private long activeUsers;
    private long totalRoles;
    private long totalPermissions;
    private Map<String, Long> userRoleDistribution;
    private Map<String, Object> facilityStatus;
    private Map<String, Object> systemHealth;

    public AdminDashboardResponse() {
    }

    public AdminDashboardResponse(long totalUsers, long activeUsers, long totalRoles, long totalPermissions, Map<String, Long> userRoleDistribution, Map<String, Object> facilityStatus, Map<String, Object> systemHealth) {
        this.totalUsers = totalUsers;
        this.activeUsers = activeUsers;
        this.totalRoles = totalRoles;
        this.totalPermissions = totalPermissions;
        this.userRoleDistribution = userRoleDistribution;
        this.facilityStatus = facilityStatus;
        this.systemHealth = systemHealth;
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

    public Map<String, Long> getUserRoleDistribution() {
        return userRoleDistribution;
    }

    public void setUserRoleDistribution(Map<String, Long> userRoleDistribution) {
        this.userRoleDistribution = userRoleDistribution;
    }

    public Map<String, Object> getFacilityStatus() {
        return facilityStatus;
    }

    public void setFacilityStatus(Map<String, Object> facilityStatus) {
        this.facilityStatus = facilityStatus;
    }

    public Map<String, Object> getSystemHealth() {
        return systemHealth;
    }

    public void setSystemHealth(Map<String, Object> systemHealth) {
        this.systemHealth = systemHealth;
    }
}

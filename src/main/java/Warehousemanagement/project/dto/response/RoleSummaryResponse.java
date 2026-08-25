package Warehousemanagement.project.dto.response;

import java.time.LocalDateTime;

public class RoleSummaryResponse {

    private Long id;
    private String name;
    private String description;
    private boolean isSystemRole;
    private int permissionCount;
    private long userCount;
    private LocalDateTime createdAt;

    public RoleSummaryResponse() {
    }

    public RoleSummaryResponse(Long id, String name, String description, boolean isSystemRole, int permissionCount, long userCount, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isSystemRole = isSystemRole;
        this.permissionCount = permissionCount;
        this.userCount = userCount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSystemRole() {
        return isSystemRole;
    }

    public void setSystemRole(boolean systemRole) {
        isSystemRole = systemRole;
    }

    public int getPermissionCount() {
        return permissionCount;
    }

    public void setPermissionCount(int permissionCount) {
        this.permissionCount = permissionCount;
    }

    public long getUserCount() {
        return userCount;
    }

    public void setUserCount(long userCount) {
        this.userCount = userCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

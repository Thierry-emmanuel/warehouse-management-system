package Warehousemanagement.project.security.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

public class RoleDetailResponse {

    private Long id;
    private String name;
    private String description;
    private boolean isSystemRole;
    private Set<PermissionSummaryResponse> permissions;
    private long userCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RoleDetailResponse() {
    }

    public RoleDetailResponse(Long id, String name, String description, boolean isSystemRole, Set<PermissionSummaryResponse> permissions, long userCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isSystemRole = isSystemRole;
        this.permissions = permissions;
        this.userCount = userCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
        this.isSystemRole = systemRole;
    }

    public Set<PermissionSummaryResponse> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionSummaryResponse> permissions) {
        this.permissions = permissions;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

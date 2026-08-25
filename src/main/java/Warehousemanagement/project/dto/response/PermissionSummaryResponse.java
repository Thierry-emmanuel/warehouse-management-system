package Warehousemanagement.project.dto.response;

import Warehousemanagement.project.enums.PermissionCategory;
import java.time.LocalDateTime;

public class PermissionSummaryResponse {

    private Long id;
    private String name;
    private String description;
    private PermissionCategory category;
    private boolean isSystemPermission;
    private long assignedRoleCount;
    private LocalDateTime createdAt;

    public PermissionSummaryResponse() {
    }

    public PermissionSummaryResponse(Long id, String name, String description, PermissionCategory category, boolean isSystemPermission, long assignedRoleCount, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.isSystemPermission = isSystemPermission;
        this.assignedRoleCount = assignedRoleCount;
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

    public PermissionCategory getCategory() {
        return category;
    }

    public void setCategory(PermissionCategory category) {
        this.category = category;
    }

    public boolean isSystemPermission() {
        return isSystemPermission;
    }

    public void setSystemPermission(boolean systemPermission) {
        isSystemPermission = systemPermission;
    }

    public long getAssignedRoleCount() {
        return assignedRoleCount;
    }

    public void setAssignedRoleCount(long assignedRoleCount) {
        this.assignedRoleCount = assignedRoleCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

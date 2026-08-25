package Warehousemanagement.project.security.dto.response;

import Warehousemanagement.project.security.enums.PermissionCategory;
import java.time.LocalDateTime;
import java.util.List;

public class PermissionDetailResponse {

    private Long id;
    private String name;
    private String description;
    private PermissionCategory category;
    private boolean isSystemPermission;
    private long assignedRoleCount;
    private List<String> assignedRoleNames;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    public PermissionDetailResponse() {
    }

    public PermissionDetailResponse(Long id, String name, String description, PermissionCategory category, boolean isSystemPermission, long assignedRoleCount, List<String> assignedRoleNames, LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.isSystemPermission = isSystemPermission;
        this.assignedRoleCount = assignedRoleCount;
        this.assignedRoleNames = assignedRoleNames;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
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
        this.isSystemPermission = systemPermission;
    }

    public long getAssignedRoleCount() {
        return assignedRoleCount;
    }

    public void setAssignedRoleCount(long assignedRoleCount) {
        this.assignedRoleCount = assignedRoleCount;
    }

    public List<String> getAssignedRoleNames() {
        return assignedRoleNames;
    }

    public void setAssignedRoleNames(List<String> assignedRoleNames) {
        this.assignedRoleNames = assignedRoleNames;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}

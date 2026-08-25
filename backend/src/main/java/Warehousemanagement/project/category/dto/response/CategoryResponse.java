package Warehousemanagement.project.category.dto.response;

import java.time.LocalDateTime;

public class CategoryResponse {

    private Long id;
    private String code;
    private String name;
    private String description;
    private boolean isActive;
    private Long parentId;
    private String parentName;
    private long subCategoryCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CategoryResponse() {
    }

    public CategoryResponse(Long id, String code, String name, String description, boolean isActive, Long parentId, String parentName, long subCategoryCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.parentId = parentId;
        this.parentName = parentName;
        this.subCategoryCount = subCategoryCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public long getSubCategoryCount() {
        return subCategoryCount;
    }

    public void setSubCategoryCount(long subCategoryCount) {
        this.subCategoryCount = subCategoryCount;
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

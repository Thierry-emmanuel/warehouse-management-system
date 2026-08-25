package Warehousemanagement.project.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateCategoryRequest {

    @NotBlank(message = "Category code is required")
    @Size(min = 2, max = 50, message = "Category code must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Z0-9_-]+$", message = "Category code must contain only uppercase letters, numbers, hyphens, and underscores")
    private String code;

    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    private Long parentId;

    public CreateCategoryRequest() {
    }

    public CreateCategoryRequest(String code, String name, String description, Long parentId) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.parentId = parentId;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}

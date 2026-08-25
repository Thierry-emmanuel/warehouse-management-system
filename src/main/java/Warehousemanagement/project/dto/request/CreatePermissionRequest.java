package Warehousemanagement.project.dto.request;

import Warehousemanagement.project.enums.PermissionCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreatePermissionRequest {

    @NotBlank(message = "Permission name is required")
    @Size(min = 3, max = 100, message = "Permission name must be between 3 and 100 characters")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Permission name must be uppercase letters, numbers, and underscores (e.g. PO_APPROVE)")
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @NotNull(message = "Permission category is required")
    private PermissionCategory category;

    public CreatePermissionRequest() {
    }

    public CreatePermissionRequest(String name, String description, PermissionCategory category) {
        this.name = name;
        this.description = description;
        this.category = category;
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
}

package Warehousemanagement.project.security.dto.request;

import Warehousemanagement.project.security.enums.PermissionCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdatePermissionRequest {

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @NotNull(message = "Permission category is required")
    private PermissionCategory category;

    public UpdatePermissionRequest() {
    }

    public UpdatePermissionRequest(String description, PermissionCategory category) {
        this.description = description;
        this.category = category;
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

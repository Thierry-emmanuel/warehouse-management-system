package Warehousemanagement.project.dto.request;

import jakarta.validation.constraints.Size;

public class UpdateRoleRequest {

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    public UpdateRoleRequest() {
    }

    public UpdateRoleRequest(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

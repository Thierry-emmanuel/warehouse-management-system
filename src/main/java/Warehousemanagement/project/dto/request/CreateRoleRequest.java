package Warehousemanagement.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class CreateRoleRequest {

    @NotBlank(message = "Role name is required")
    @Size(min = 3, max = 100, message = "Role name must be between 3 and 100 characters")
    @Pattern(regexp = "^ROLE_[A-Z0-9_]+$", message = "Role name must start with 'ROLE_' and contain only uppercase letters/underscores (e.g. ROLE_SUPERVISOR)")
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    private Set<Long> permissionIds = new HashSet<>();

    public CreateRoleRequest() {
    }

    public CreateRoleRequest(String name, String description, Set<Long> permissionIds) {
        this.name = name;
        this.description = description;
        this.permissionIds = permissionIds;
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

    public Set<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(Set<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
}

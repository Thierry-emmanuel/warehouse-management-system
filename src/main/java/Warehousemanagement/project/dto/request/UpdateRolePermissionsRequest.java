package Warehousemanagement.project.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class UpdateRolePermissionsRequest {

    @NotNull(message = "Permission IDs set cannot be null")
    private Set<Long> permissionIds = new HashSet<>();

    public UpdateRolePermissionsRequest() {
    }

    public UpdateRolePermissionsRequest(Set<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }

    public Set<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(Set<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
}

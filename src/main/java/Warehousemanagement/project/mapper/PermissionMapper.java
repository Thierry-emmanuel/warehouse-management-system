package Warehousemanagement.project.mapper;

import Warehousemanagement.project.dto.request.CreatePermissionRequest;
import Warehousemanagement.project.dto.response.PermissionDetailResponse;
import Warehousemanagement.project.dto.response.PermissionSummaryResponse;
import Warehousemanagement.project.model.Permission;
import Warehousemanagement.project.model.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionMapper {

    public Permission toEntity(CreatePermissionRequest request) {
        if (request == null) {
            return null;
        }
        Permission permission = new Permission();
        permission.setName(request.getName().toUpperCase().trim());
        permission.setDescription(request.getDescription());
        permission.setCategory(request.getCategory());
        permission.setSystemPermission(false);
        return permission;
    }

    public PermissionSummaryResponse toSummaryResponse(Permission permission, long assignedRoleCount) {
        if (permission == null) {
            return null;
        }
        return new PermissionSummaryResponse(
            permission.getId(),
            permission.getName(),
            permission.getDescription(),
            permission.getCategory(),
            permission.isSystemPermission(),
            assignedRoleCount,
            permission.getCreatedAt()
        );
    }

    public PermissionDetailResponse toDetailResponse(Permission permission, long assignedRoleCount) {
        if (permission == null) {
            return null;
        }
        List<String> assignedRoleNames = permission.getRoles() != null
            ? permission.getRoles().stream().map(Role::getName).sorted().collect(Collectors.toList())
            : List.of();

        return new PermissionDetailResponse(
            permission.getId(),
            permission.getName(),
            permission.getDescription(),
            permission.getCategory(),
            permission.isSystemPermission(),
            assignedRoleCount,
            assignedRoleNames,
            permission.getCreatedAt(),
            permission.getUpdatedAt(),
            permission.getCreatedBy(),
            permission.getUpdatedBy()
        );
    }
}

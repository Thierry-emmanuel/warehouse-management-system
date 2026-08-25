package Warehousemanagement.project.security.mapper;

import Warehousemanagement.project.security.dto.request.CreatePermissionRequest;
import Warehousemanagement.project.security.dto.response.PermissionDetailResponse;
import Warehousemanagement.project.security.dto.response.PermissionSummaryResponse;
import Warehousemanagement.project.security.model.Permission;
import Warehousemanagement.project.security.model.Role;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PermissionMapper {

    public Permission toEntity(CreatePermissionRequest request) {
        if (request == null) {
            return null;
        }
        return new Permission(
            request.getName().trim().toUpperCase(),
            request.getDescription(),
            request.getCategory(),
            false
        );
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
        List<String> roleNames = new ArrayList<>();
        if (permission.getRoles() != null) {
            roleNames = permission.getRoles().stream()
                .map(Role::getName)
                .sorted()
                .toList();
        }

        return new PermissionDetailResponse(
            permission.getId(),
            permission.getName(),
            permission.getDescription(),
            permission.getCategory(),
            permission.isSystemPermission(),
            assignedRoleCount,
            roleNames,
            permission.getCreatedAt(),
            permission.getUpdatedAt(),
            permission.getCreatedBy(),
            permission.getUpdatedBy()
        );
    }
}

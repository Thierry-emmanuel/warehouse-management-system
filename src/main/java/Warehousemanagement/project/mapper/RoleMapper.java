package Warehousemanagement.project.mapper;

import Warehousemanagement.project.dto.request.CreateRoleRequest;
import Warehousemanagement.project.dto.response.PermissionSummaryResponse;
import Warehousemanagement.project.dto.response.RoleDetailResponse;
import Warehousemanagement.project.dto.response.RoleSummaryResponse;
import Warehousemanagement.project.model.Role;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    private final PermissionMapper permissionMapper;

    public RoleMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    public Role toEntity(CreateRoleRequest request) {
        if (request == null) {
            return null;
        }
        Role role = new Role();
        role.setName(request.getName().toUpperCase().trim());
        role.setDescription(request.getDescription());
        role.setSystemRole(false);
        return role;
    }

    public RoleSummaryResponse toSummaryResponse(Role role, long userCount) {
        if (role == null) {
            return null;
        }
        int permissionCount = role.getPermissions() != null ? role.getPermissions().size() : 0;
        return new RoleSummaryResponse(
            role.getId(),
            role.getName(),
            role.getDescription(),
            role.isSystemRole(),
            permissionCount,
            userCount,
            role.getCreatedAt()
        );
    }

    public RoleDetailResponse toDetailResponse(Role role, long userCount) {
        if (role == null) {
            return null;
        }
        Set<PermissionSummaryResponse> permissionResponses = role.getPermissions() != null
            ? role.getPermissions().stream()
                .map(p -> permissionMapper.toSummaryResponse(p, 0))
                .collect(Collectors.toSet())
            : Collections.emptySet();

        return new RoleDetailResponse(
            role.getId(),
            role.getName(),
            role.getDescription(),
            role.isSystemRole(),
            permissionResponses,
            userCount,
            role.getCreatedAt(),
            role.getUpdatedAt()
        );
    }
}

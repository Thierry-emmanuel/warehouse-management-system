package Warehousemanagement.project.security.mapper;

import Warehousemanagement.project.security.dto.request.CreateRoleRequest;
import Warehousemanagement.project.security.dto.response.PermissionSummaryResponse;
import Warehousemanagement.project.security.dto.response.RoleDetailResponse;
import Warehousemanagement.project.security.dto.response.RoleSummaryResponse;
import Warehousemanagement.project.security.model.Role;
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
        return new Role(
            request.getName().trim().toUpperCase(),
            request.getDescription(),
            false
        );
    }

    public RoleSummaryResponse toSummaryResponse(Role role, long userCount) {
        if (role == null) {
            return null;
        }
        int permCount = role.getPermissions() != null ? role.getPermissions().size() : 0;
        return new RoleSummaryResponse(
            role.getId(),
            role.getName(),
            role.getDescription(),
            role.isSystemRole(),
            permCount,
            userCount,
            role.getCreatedAt()
        );
    }

    public RoleDetailResponse toDetailResponse(Role role, long userCount) {
        if (role == null) {
            return null;
        }
        Set<PermissionSummaryResponse> perms = Collections.emptySet();
        if (role.getPermissions() != null) {
            perms = role.getPermissions().stream()
                .map(p -> permissionMapper.toSummaryResponse(p, 0L))
                .collect(Collectors.toSet());
        }

        return new RoleDetailResponse(
            role.getId(),
            role.getName(),
            role.getDescription(),
            role.isSystemRole(),
            perms,
            userCount,
            role.getCreatedAt(),
            role.getUpdatedAt()
        );
    }
}

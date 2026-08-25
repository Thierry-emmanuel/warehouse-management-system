package Warehousemanagement.project.mapper;

import Warehousemanagement.project.dto.request.CreateUserRequest;
import Warehousemanagement.project.dto.response.RoleSummaryResponse;
import Warehousemanagement.project.dto.response.UserDetailResponse;
import Warehousemanagement.project.dto.response.UserSummaryResponse;
import Warehousemanagement.project.model.Permission;
import Warehousemanagement.project.model.Role;
import Warehousemanagement.project.model.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final RoleMapper roleMapper;

    public UserMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public User toEntity(CreateUserRequest request, String encodedPassword) {
        if (request == null) {
            return null;
        }
        User user = new User();
        user.setUsername(request.getUsername().toLowerCase().trim());
        user.setEmail(request.getEmail().toLowerCase().trim());
        user.setFullName(request.getFullName().trim());
        user.setPassword(encodedPassword);
        user.setPhoneNumber(request.getPhoneNumber());
        user.setWarehouseId(request.getWarehouseId());
        user.setActive(true);
        return user;
    }

    public UserSummaryResponse toSummaryResponse(User user) {
        if (user == null) {
            return null;
        }
        Set<String> roleNames = user.getRoles() != null
            ? user.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
            : Collections.emptySet();

        return new UserSummaryResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFullName(),
            user.getPhoneNumber(),
            user.isActive(),
            user.getWarehouseId(),
            roleNames,
            user.getCreatedAt()
        );
    }

    public UserDetailResponse toDetailResponse(User user) {
        if (user == null) {
            return null;
        }
        Set<RoleSummaryResponse> roleSummaries = user.getRoles() != null
            ? user.getRoles().stream()
                .map(r -> roleMapper.toSummaryResponse(r, 0))
                .collect(Collectors.toSet())
            : Collections.emptySet();

        Set<String> distinctPermissions = new HashSet<>();
        if (user.getRoles() != null) {
            for (Role role : user.getRoles()) {
                if (role.getPermissions() != null) {
                    for (Permission perm : role.getPermissions()) {
                        distinctPermissions.add(perm.getName());
                    }
                }
            }
        }

        return new UserDetailResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFullName(),
            user.getPhoneNumber(),
            user.isActive(),
            user.getWarehouseId(),
            roleSummaries,
            distinctPermissions,
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }
}

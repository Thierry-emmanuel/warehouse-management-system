package Warehousemanagement.project.security.mapper;

import Warehousemanagement.project.security.dto.request.CreateUserRequest;
import Warehousemanagement.project.security.dto.response.RoleSummaryResponse;
import Warehousemanagement.project.security.dto.response.UserDetailResponse;
import Warehousemanagement.project.security.dto.response.UserSummaryResponse;
import Warehousemanagement.project.security.model.Role;
import Warehousemanagement.project.security.model.User;
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
        User user = new User(
            request.getUsername().trim().toLowerCase(),
            request.getEmail().trim().toLowerCase(),
            request.getFullName().trim(),
            encodedPassword,
            request.getWarehouseId()
        );
        user.setPhoneNumber(request.getPhoneNumber());
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
        Set<RoleSummaryResponse> roles = Collections.emptySet();
        Set<String> permissions = new HashSet<>();

        if (user.getRoles() != null) {
            roles = user.getRoles().stream()
                .map(r -> roleMapper.toSummaryResponse(r, 0L))
                .collect(Collectors.toSet());

            for (Role role : user.getRoles()) {
                if (role.getPermissions() != null) {
                    role.getPermissions().forEach(p -> permissions.add(p.getName()));
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
            roles,
            permissions,
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }
}

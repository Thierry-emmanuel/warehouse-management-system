package Warehousemanagement.project.security.service.impl;

import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.common.exceptions.DuplicateResourceException;
import Warehousemanagement.project.common.exceptions.ResourceNotFoundException;
import Warehousemanagement.project.common.exceptions.UnauthorizedAccessException;
import Warehousemanagement.project.security.dto.request.CreateUserRequest;
import Warehousemanagement.project.security.dto.request.UpdateUserRequest;
import Warehousemanagement.project.security.dto.response.UserDetailResponse;
import Warehousemanagement.project.security.dto.response.UserSummaryResponse;
import Warehousemanagement.project.security.mapper.UserMapper;
import Warehousemanagement.project.security.model.Role;
import Warehousemanagement.project.security.model.User;
import Warehousemanagement.project.security.repository.RoleRepository;
import Warehousemanagement.project.security.repository.UserRepository;
import Warehousemanagement.project.security.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetailResponse createUser(CreateUserRequest request) {
        String normalizedUsername = request.getUsername().trim().toLowerCase();
        String normalizedEmail = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByUsername(normalizedUsername)) {
            throw new DuplicateResourceException("User", "username", normalizedUsername);
        }
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new DuplicateResourceException("User", "email", normalizedEmail);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = userMapper.toEntity(request, encodedPassword);

        if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
            Set<Role> roles = roleRepository.findAllByIdIn(request.getRoleIds());
            user.setRoles(roles);
        }

        User saved = userRepository.save(user);
        return userMapper.toDetailResponse(saved);
    }

    @Override
    @Transactional
    public UserDetailResponse updateUser(Long id, UpdateUserRequest request, Long callerWarehouseId) {
        User user = userRepository.findWithRolesAndPermissionsById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        validateWarehouseAccess(user, callerWarehouseId);

        String normalizedEmail = request.getEmail().trim().toLowerCase();
        if (!user.getEmail().equalsIgnoreCase(normalizedEmail) && userRepository.existsByEmail(normalizedEmail)) {
            throw new DuplicateResourceException("User", "email", normalizedEmail);
        }

        user.setEmail(normalizedEmail);
        user.setFullName(request.getFullName().trim());
        user.setPhoneNumber(request.getPhoneNumber());

        if (request.getIsActive() != null) {
            user.setActive(request.getIsActive());
        }

        if (request.getWarehouseId() != null) {
            user.setWarehouseId(request.getWarehouseId());
        }

        if (request.getRoleIds() != null) {
            Set<Role> roles = new HashSet<>();
            if (!request.getRoleIds().isEmpty()) {
                roles = roleRepository.findAllByIdIn(request.getRoleIds());
            }
            user.setRoles(roles);
        }

        User updated = userRepository.save(user);
        return userMapper.toDetailResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailResponse getUserById(Long id, Long callerWarehouseId) {
        User user = userRepository.findWithRolesAndPermissionsById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        validateWarehouseAccess(user, callerWarehouseId);
        return userMapper.toDetailResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<UserSummaryResponse> getAllUsersInWarehouse(Long warehouseId, String query, Pageable pageable) {
        Page<User> page;
        if (query != null && !query.trim().isEmpty()) {
            page = userRepository.searchUsersInWarehouse(warehouseId, query.trim(), pageable);
        } else {
            page = userRepository.findByWarehouseId(warehouseId, pageable);
        }

        List<UserSummaryResponse> content = page.getContent().stream()
            .map(userMapper::toSummaryResponse)
            .toList();

        return PagedResponse.from(page, content);
    }

    @Override
    @Transactional
    public void setUserActiveStatus(Long id, boolean isActive, Long callerWarehouseId) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        validateWarehouseAccess(user, callerWarehouseId);
        user.setActive(isActive);
        userRepository.save(user);
    }

    private void validateWarehouseAccess(User targetUser, Long callerWarehouseId) {
        if (callerWarehouseId != null && !Objects.equals(targetUser.getWarehouseId(), callerWarehouseId)) {
            throw new UnauthorizedAccessException("Cannot access or modify user belonging to facility " + targetUser.getWarehouseId());
        }
    }
}

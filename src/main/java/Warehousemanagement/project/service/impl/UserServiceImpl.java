package Warehousemanagement.project.service.impl;

import Warehousemanagement.project.dto.request.CreateUserRequest;
import Warehousemanagement.project.dto.request.UpdateUserRequest;
import Warehousemanagement.project.dto.response.PagedResponse;
import Warehousemanagement.project.dto.response.UserDetailResponse;
import Warehousemanagement.project.dto.response.UserSummaryResponse;
import Warehousemanagement.project.exceptions.DuplicateResourceException;
import Warehousemanagement.project.exceptions.ResourceNotFoundException;
import Warehousemanagement.project.exceptions.UnauthorizedAccessException;
import Warehousemanagement.project.mapper.UserMapper;
import Warehousemanagement.project.model.Role;
import Warehousemanagement.project.model.User;
import Warehousemanagement.project.repository.RoleRepository;
import Warehousemanagement.project.repository.UserRepository;
import Warehousemanagement.project.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetailResponse createUser(CreateUserRequest request) {
        String normalizedUsername = request.getUsername().toLowerCase().trim();
        String normalizedEmail = request.getEmail().toLowerCase().trim();

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

        String normalizedEmail = request.getEmail().toLowerCase().trim();
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
        if (StringUtils.hasText(query)) {
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

    private void validateWarehouseAccess(User user, Long callerWarehouseId) {
        if (callerWarehouseId != null && !callerWarehouseId.equals(user.getWarehouseId())) {
            throw new UnauthorizedAccessException("Access denied: User does not belong to your authorized warehouse facility.");
        }
    }
}

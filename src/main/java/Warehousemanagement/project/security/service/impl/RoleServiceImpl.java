package Warehousemanagement.project.security.service.impl;

import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.common.exceptions.BusinessRuleException;
import Warehousemanagement.project.common.exceptions.DuplicateResourceException;
import Warehousemanagement.project.common.exceptions.ResourceNotFoundException;
import Warehousemanagement.project.security.dto.request.CreateRoleRequest;
import Warehousemanagement.project.security.dto.request.UpdateRolePermissionsRequest;
import Warehousemanagement.project.security.dto.request.UpdateRoleRequest;
import Warehousemanagement.project.security.dto.response.RoleDetailResponse;
import Warehousemanagement.project.security.dto.response.RoleSummaryResponse;
import Warehousemanagement.project.security.mapper.RoleMapper;
import Warehousemanagement.project.security.model.Permission;
import Warehousemanagement.project.security.model.Role;
import Warehousemanagement.project.security.repository.PermissionRepository;
import Warehousemanagement.project.security.repository.RoleRepository;
import Warehousemanagement.project.security.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository,
                           PermissionRepository permissionRepository,
                           RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    @Transactional
    public RoleDetailResponse createRole(CreateRoleRequest request) {
        String normalizedName = request.getName().trim().toUpperCase();
        if (roleRepository.existsByName(normalizedName)) {
            throw new DuplicateResourceException("Role", "name", normalizedName);
        }

        Role role = roleMapper.toEntity(request);

        if (request.getPermissionIds() != null && !request.getPermissionIds().isEmpty()) {
            Set<Permission> perms = permissionRepository.findAllByIdIn(request.getPermissionIds());
            role.setPermissions(perms);
        }

        Role saved = roleRepository.save(role);
        return roleMapper.toDetailResponse(saved, 0L);
    }

    @Override
    @Transactional
    public RoleDetailResponse updateRole(Long id, UpdateRoleRequest request) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

        role.setDescription(request.getDescription());
        Role updated = roleRepository.save(role);
        long userCount = roleRepository.countAssignedUsers(id);
        return roleMapper.toDetailResponse(updated, userCount);
    }

    @Override
    @Transactional
    public RoleDetailResponse updateRolePermissions(Long id, UpdateRolePermissionsRequest request) {
        Role role = roleRepository.findWithPermissionsById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

        Set<Permission> permissions = new HashSet<>();
        if (request.getPermissionIds() != null && !request.getPermissionIds().isEmpty()) {
            permissions = permissionRepository.findAllByIdIn(request.getPermissionIds());
        }

        role.setPermissions(permissions);
        Role updated = roleRepository.save(role);
        long userCount = roleRepository.countAssignedUsers(id);
        return roleMapper.toDetailResponse(updated, userCount);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDetailResponse getRoleById(Long id) {
        Role role = roleRepository.findWithPermissionsById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

        long userCount = roleRepository.countAssignedUsers(id);
        return roleMapper.toDetailResponse(role, userCount);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<RoleSummaryResponse> getAllRoles(String query, Pageable pageable) {
        Page<Role> page;
        if (query != null && !query.trim().isEmpty()) {
            page = roleRepository.searchRoles(query.trim(), pageable);
        } else {
            page = roleRepository.findAll(pageable);
        }

        List<RoleSummaryResponse> content = page.getContent().stream()
            .map(r -> {
                long userCount = roleRepository.countAssignedUsers(r.getId());
                return roleMapper.toSummaryResponse(r, userCount);
            })
            .toList();

        return PagedResponse.from(page, content);
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

        if (role.isSystemRole()) {
            throw new BusinessRuleException("System role '" + role.getName() + "' is critical and cannot be deleted");
        }

        long assignedUsers = roleRepository.countAssignedUsers(id);
        if (assignedUsers > 0) {
            throw new BusinessRuleException("Role '" + role.getName() + "' is actively assigned to " + assignedUsers + " user(s). Reassign users before deletion.");
        }

        roleRepository.delete(role);
    }
}

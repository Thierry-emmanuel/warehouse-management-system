package Warehousemanagement.project.service.impl;

import Warehousemanagement.project.dto.request.CreatePermissionRequest;
import Warehousemanagement.project.dto.request.UpdatePermissionRequest;
import Warehousemanagement.project.dto.response.PagedResponse;
import Warehousemanagement.project.dto.response.PermissionDetailResponse;
import Warehousemanagement.project.dto.response.PermissionSummaryResponse;
import Warehousemanagement.project.enums.PermissionCategory;
import Warehousemanagement.project.exceptions.BusinessRuleException;
import Warehousemanagement.project.exceptions.DuplicateResourceException;
import Warehousemanagement.project.exceptions.ResourceNotFoundException;
import Warehousemanagement.project.mapper.PermissionMapper;
import Warehousemanagement.project.model.Permission;
import Warehousemanagement.project.model.Role;
import Warehousemanagement.project.repository.PermissionRepository;
import Warehousemanagement.project.repository.RoleRepository;
import Warehousemanagement.project.service.PermissionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final PermissionMapper permissionMapper;

    public PermissionServiceImpl(PermissionRepository permissionRepository, RoleRepository roleRepository, PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.permissionMapper = permissionMapper;
    }

    @Override
    @Transactional
    public PermissionDetailResponse createPermission(CreatePermissionRequest request) {
        String normalizedName = request.getName().toUpperCase().trim();
        if (permissionRepository.existsByName(normalizedName)) {
            throw new DuplicateResourceException("Permission", "name", normalizedName);
        }

        Permission permission = permissionMapper.toEntity(request);
        Permission saved = permissionRepository.save(permission);
        return permissionMapper.toDetailResponse(saved, 0);
    }

    @Override
    @Transactional
    public PermissionDetailResponse updatePermission(Long id, UpdatePermissionRequest request) {
        Permission permission = permissionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", id));

        permission.setDescription(request.getDescription());
        permission.setCategory(request.getCategory());
        Permission updated = permissionRepository.save(permission);

        long roleCount = permissionRepository.countAssignedRoles(id);
        return permissionMapper.toDetailResponse(updated, roleCount);
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionDetailResponse getPermissionById(Long id) {
        Permission permission = permissionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", id));

        long roleCount = permissionRepository.countAssignedRoles(id);
        return permissionMapper.toDetailResponse(permission, roleCount);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<PermissionSummaryResponse> getAllPermissions(String query, PermissionCategory category, Pageable pageable) {
        Page<Permission> page;
        if (StringUtils.hasText(query)) {
            page = permissionRepository.searchPermissions(query.trim(), pageable);
        } else if (category != null) {
            page = permissionRepository.findByCategory(category, pageable);
        } else {
            page = permissionRepository.findAll(pageable);
        }

        List<PermissionSummaryResponse> content = page.getContent().stream()
            .map(p -> {
                long roleCount = permissionRepository.countAssignedRoles(p.getId());
                return permissionMapper.toSummaryResponse(p, roleCount);
            })
            .toList();

        return PagedResponse.from(page, content);
    }

    @Override
    @Transactional
    public void deletePermission(Long id) {
        Permission permission = permissionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", id));

        if (permission.isSystemPermission()) {
            throw new BusinessRuleException("System-defined permissions cannot be deleted.");
        }

        // Clean up join references in roles before deletion
        if (permission.getRoles() != null) {
            for (Role role : permission.getRoles()) {
                role.getPermissions().remove(permission);
                roleRepository.save(role);
            }
        }

        permissionRepository.delete(permission);
    }
}

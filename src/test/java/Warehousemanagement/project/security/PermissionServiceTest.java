package Warehousemanagement.project.security;

import Warehousemanagement.project.common.exceptions.BusinessRuleException;
import Warehousemanagement.project.common.exceptions.DuplicateResourceException;
import Warehousemanagement.project.common.exceptions.ResourceNotFoundException;
import Warehousemanagement.project.security.dto.request.CreatePermissionRequest;
import Warehousemanagement.project.security.dto.request.UpdatePermissionRequest;
import Warehousemanagement.project.security.dto.response.PermissionDetailResponse;
import Warehousemanagement.project.security.enums.PermissionCategory;
import Warehousemanagement.project.security.mapper.PermissionMapper;
import Warehousemanagement.project.security.model.Permission;
import Warehousemanagement.project.security.repository.PermissionRepository;
import Warehousemanagement.project.security.repository.RoleRepository;
import Warehousemanagement.project.security.service.impl.PermissionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PermissionServiceTest {

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private RoleRepository roleRepository;

    @Spy
    private PermissionMapper permissionMapper = new PermissionMapper();

    @InjectMocks
    private PermissionServiceImpl permissionService;

    private Permission testPermission;

    @BeforeEach
    void setUp() {
        testPermission = new Permission("PO_APPROVE", "Approve purchase orders", PermissionCategory.PROCUREMENT, false);
        testPermission.setId(1L);
    }

    @Test
    @DisplayName("Should successfully create a dynamic permission")
    void shouldCreatePermissionSuccessfully() {
        CreatePermissionRequest request = new CreatePermissionRequest("PO_APPROVE", "Approve purchase orders", PermissionCategory.PROCUREMENT);

        when(permissionRepository.existsByName("PO_APPROVE")).thenReturn(false);
        when(permissionRepository.save(any(Permission.class))).thenReturn(testPermission);

        PermissionDetailResponse response = permissionService.createPermission(request);

        assertNotNull(response);
        assertEquals("PO_APPROVE", response.getName());
        assertEquals(PermissionCategory.PROCUREMENT, response.getCategory());
        verify(permissionRepository).save(any(Permission.class));
    }

    @Test
    @DisplayName("Should reject duplicate permission creation")
    void shouldThrowWhenCreatingDuplicatePermission() {
        CreatePermissionRequest request = new CreatePermissionRequest("PO_APPROVE", "Approve purchase orders", PermissionCategory.PROCUREMENT);

        when(permissionRepository.existsByName("PO_APPROVE")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> permissionService.createPermission(request));
        verify(permissionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should successfully update permission description and category")
    void shouldUpdatePermissionSuccessfully() {
        UpdatePermissionRequest request = new UpdatePermissionRequest("Updated description", PermissionCategory.OPERATIONS);

        when(permissionRepository.findById(1L)).thenReturn(Optional.of(testPermission));
        when(permissionRepository.save(any(Permission.class))).thenReturn(testPermission);
        when(permissionRepository.countAssignedRoles(1L)).thenReturn(2L);

        PermissionDetailResponse response = permissionService.updatePermission(1L, request);

        assertNotNull(response);
        assertEquals(2L, response.getAssignedRoleCount());
        verify(permissionRepository).save(testPermission);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when updating non-existent permission")
    void shouldThrowWhenPermissionNotFound() {
        UpdatePermissionRequest request = new UpdatePermissionRequest("Updated", PermissionCategory.OPERATIONS);
        when(permissionRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> permissionService.updatePermission(999L, request));
    }

    @Test
    @DisplayName("Should guard system permissions against deletion")
    void shouldPreventDeletingSystemPermission() {
        Permission systemPerm = new Permission("USER_MANAGE", "System perm", PermissionCategory.ADMINISTRATION, true);
        systemPerm.setId(2L);

        when(permissionRepository.findById(2L)).thenReturn(Optional.of(systemPerm));

        assertThrows(BusinessRuleException.class, () -> permissionService.deletePermission(2L));
        verify(permissionRepository, never()).delete(any());
    }
}

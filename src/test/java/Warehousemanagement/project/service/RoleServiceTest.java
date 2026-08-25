package Warehousemanagement.project.service;

import Warehousemanagement.project.dto.request.CreateRoleRequest;
import Warehousemanagement.project.dto.response.RoleDetailResponse;
import Warehousemanagement.project.enums.PermissionCategory;
import Warehousemanagement.project.exceptions.BusinessRuleException;
import Warehousemanagement.project.exceptions.DuplicateResourceException;
import Warehousemanagement.project.mapper.PermissionMapper;
import Warehousemanagement.project.mapper.RoleMapper;
import Warehousemanagement.project.model.Permission;
import Warehousemanagement.project.model.Role;
import Warehousemanagement.project.repository.PermissionRepository;
import Warehousemanagement.project.repository.RoleRepository;
import Warehousemanagement.project.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @Spy
    private RoleMapper roleMapper = new RoleMapper(new PermissionMapper());

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role testRole;
    private Permission testPerm;

    @BeforeEach
    void setUp() {
        testPerm = new Permission("PICK_EXECUTE", "Pick items", PermissionCategory.OPERATIONS, false);
        testPerm.setId(10L);

        testRole = new Role("ROLE_DISPATCHER", "Dispatcher role", false);
        testRole.setId(1L);
        testRole.setPermissions(Set.of(testPerm));
    }

    @Test
    @DisplayName("Should successfully create a dynamic role with assigned permissions")
    void shouldCreateRoleSuccessfully() {
        CreateRoleRequest request = new CreateRoleRequest("ROLE_DISPATCHER", "Dispatcher role", Set.of(10L));

        when(roleRepository.existsByName("ROLE_DISPATCHER")).thenReturn(false);
        when(permissionRepository.findAllByIdIn(Set.of(10L))).thenReturn(Set.of(testPerm));
        when(roleRepository.save(any(Role.class))).thenReturn(testRole);

        RoleDetailResponse response = roleService.createRole(request);

        assertNotNull(response);
        assertEquals("ROLE_DISPATCHER", response.getName());
        assertEquals(1, response.getPermissions().size());
        verify(roleRepository).save(any(Role.class));
    }

    @Test
    @DisplayName("Should reject duplicate role creation")
    void shouldThrowWhenCreatingDuplicateRole() {
        CreateRoleRequest request = new CreateRoleRequest("ROLE_DISPATCHER", "Dispatcher role", Set.of(10L));
        when(roleRepository.existsByName("ROLE_DISPATCHER")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> roleService.createRole(request));
        verify(roleRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should guard core system roles from deletion")
    void shouldPreventDeletingSystemRole() {
        Role systemRole = new Role("ROLE_ADMIN", "System Admin", true);
        systemRole.setId(1L);

        when(roleRepository.findById(1L)).thenReturn(Optional.of(systemRole));

        assertThrows(BusinessRuleException.class, () -> roleService.deleteRole(1L));
        verify(roleRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Should guard active assigned roles from deletion")
    void shouldPreventDeletingRoleWithActiveUsers() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(testRole));
        when(roleRepository.countAssignedUsers(1L)).thenReturn(3L);

        assertThrows(BusinessRuleException.class, () -> roleService.deleteRole(1L));
        verify(roleRepository, never()).delete(any());
    }
}

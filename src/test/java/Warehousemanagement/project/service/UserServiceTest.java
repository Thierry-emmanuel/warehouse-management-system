package Warehousemanagement.project.service;

import Warehousemanagement.project.dto.request.CreateUserRequest;
import Warehousemanagement.project.dto.response.UserDetailResponse;
import Warehousemanagement.project.exceptions.DuplicateResourceException;
import Warehousemanagement.project.exceptions.UnauthorizedAccessException;
import Warehousemanagement.project.mapper.PermissionMapper;
import Warehousemanagement.project.mapper.RoleMapper;
import Warehousemanagement.project.mapper.UserMapper;
import Warehousemanagement.project.model.Role;
import Warehousemanagement.project.model.User;
import Warehousemanagement.project.repository.RoleRepository;
import Warehousemanagement.project.repository.UserRepository;
import Warehousemanagement.project.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

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
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Spy
    private UserMapper userMapper = new UserMapper(new RoleMapper(new PermissionMapper()));

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private Role testRole;

    @BeforeEach
    void setUp() {
        testRole = new Role("ROLE_EMPLOYEE", "Warehouse Floor Staff", true);
        testRole.setId(3L);

        testUser = new User("john_doe", "john@wms.com", "John Doe", "encodedPass", 1L);
        testUser.setId(10L);
        testUser.setRoles(Set.of(testRole));
    }

    @Test
    @DisplayName("Should successfully create a user with hashed password and assigned roles")
    void shouldCreateUserSuccessfully() {
        CreateUserRequest request = new CreateUserRequest("john_doe", "john@wms.com", "John Doe", "RawPass123", 1L, Set.of(3L));

        when(userRepository.existsByUsername("john_doe")).thenReturn(false);
        when(userRepository.existsByEmail("john@wms.com")).thenReturn(false);
        when(passwordEncoder.encode("RawPass123")).thenReturn("encodedPass");
        when(roleRepository.findAllByIdIn(Set.of(3L))).thenReturn(Set.of(testRole));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserDetailResponse response = userService.createUser(request);

        assertNotNull(response);
        assertEquals("john_doe", response.getUsername());
        assertEquals("john@wms.com", response.getEmail());
        assertEquals(1, response.getRoles().size());
        verify(passwordEncoder).encode("RawPass123");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should reject duplicate username creation")
    void shouldThrowWhenUsernameExists() {
        CreateUserRequest request = new CreateUserRequest("john_doe", "john@wms.com", "John Doe", "RawPass123", 1L, Set.of());
        when(userRepository.existsByUsername("john_doe")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> userService.createUser(request));
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should enforce warehouse facility isolation when accessing user across different warehouse")
    void shouldBlockCrossWarehouseUserAccess() {
        when(userRepository.findWithRolesAndPermissionsById(10L)).thenReturn(Optional.of(testUser)); // user belongs to warehouseId 1

        assertThrows(UnauthorizedAccessException.class, () -> userService.getUserById(10L, 2L)); // caller belongs to warehouseId 2
    }
}

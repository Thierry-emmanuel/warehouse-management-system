package Warehousemanagement.project.security.service.impl;

import Warehousemanagement.project.common.exceptions.BadRequestException;
import Warehousemanagement.project.common.exceptions.ResourceNotFoundException;
import Warehousemanagement.project.security.config.CustomUserDetails;
import Warehousemanagement.project.security.config.JwtTokenProvider;
import Warehousemanagement.project.security.dto.request.LoginRequest;
import Warehousemanagement.project.security.dto.request.RegisterRequest;
import Warehousemanagement.project.security.dto.response.AuthResponse;
import Warehousemanagement.project.security.enums.SystemRoleType;
import Warehousemanagement.project.security.model.Role;
import Warehousemanagement.project.security.model.User;
import Warehousemanagement.project.security.repository.RoleRepository;
import Warehousemanagement.project.security.repository.UserRepository;
import Warehousemanagement.project.security.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider,
                           UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername().trim(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();

        userDetails.getAuthorities().forEach(authority -> {
            String auth = authority.getAuthority();
            if (auth.startsWith("ROLE_")) {
                roles.add(auth);
            } else {
                permissions.add(auth);
            }
        });

        return new AuthResponse(
            jwt,
            tokenProvider.getExpirationInSeconds(),
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            userDetails.getUsername(),
            userDetails.getWarehouseId(),
            roles,
            permissions
        );
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername().trim())) {
            throw new BadRequestException("Username '" + request.getUsername() + "' is already taken.");
        }
        if (userRepository.existsByEmail(request.getEmail().trim())) {
            throw new BadRequestException("Email '" + request.getEmail() + "' is already registered.");
        }

        User user = new User(
            request.getUsername().trim(),
            request.getEmail().trim(),
            request.getFullName().trim(),
            passwordEncoder.encode(request.getPassword()),
            request.getWarehouseId() != null ? request.getWarehouseId() : 1L
        );
        user.setPhoneNumber(request.getPhoneNumber());

        Role defaultRole = roleRepository.findByName(SystemRoleType.ROLE_EMPLOYEE.name())
            .orElseGet(() -> roleRepository.save(new Role(SystemRoleType.ROLE_EMPLOYEE.name(), "Floor Operations Specialist", true)));
        user.setRoles(Set.of(defaultRole));

        userRepository.save(user);

        return login(new LoginRequest(request.getUsername(), request.getPassword()));
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse getCurrentUser(String username) {
        User user = userRepository.findWithRolesAndPermissionsByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        Set<String> roles = user.getRoles() != null
            ? user.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
            : Set.of();

        Set<String> permissions = new HashSet<>();
        if (user.getRoles() != null) {
            user.getRoles().forEach(r -> {
                if (r.getPermissions() != null) {
                    r.getPermissions().forEach(p -> permissions.add(p.getName()));
                }
            });
        }

        return new AuthResponse(
            null,
            null,
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFullName(),
            user.getWarehouseId(),
            roles,
            permissions
        );
    }
}

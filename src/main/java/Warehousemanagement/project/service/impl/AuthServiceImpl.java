package Warehousemanagement.project.service.impl;

import Warehousemanagement.project.config.CustomUserDetails;
import Warehousemanagement.project.config.JwtTokenProvider;
import Warehousemanagement.project.dto.request.LoginRequest;
import Warehousemanagement.project.dto.response.AuthResponse;
import Warehousemanagement.project.exceptions.ResourceNotFoundException;
import Warehousemanagement.project.model.Permission;
import Warehousemanagement.project.model.Role;
import Warehousemanagement.project.model.User;
import Warehousemanagement.project.repository.UserRepository;
import Warehousemanagement.project.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername().trim(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String token = tokenProvider.generateToken(userDetails);
        User user = userRepository.findWithRolesAndPermissionsByUsername(userDetails.getUsername())
            .orElseThrow(() -> new ResourceNotFoundException("User", "username", userDetails.getUsername()));

        return buildAuthResponse(user, token);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse getCurrentUser(String username) {
        User user = userRepository.findWithRolesAndPermissionsByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        return buildAuthResponse(user, null);
    }

    private AuthResponse buildAuthResponse(User user, String token) {
        Set<String> roles = user.getRoles() != null
            ? user.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
            : Set.of();

        Set<String> permissions = new HashSet<>();
        if (user.getRoles() != null) {
            for (Role role : user.getRoles()) {
                if (role.getPermissions() != null) {
                    for (Permission perm : role.getPermissions()) {
                        permissions.add(perm.getName());
                    }
                }
            }
        }

        return new AuthResponse(
            token,
            tokenProvider.getExpirationMs(),
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

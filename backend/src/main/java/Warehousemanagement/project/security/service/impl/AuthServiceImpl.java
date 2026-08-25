package Warehousemanagement.project.security.service.impl;

import Warehousemanagement.project.common.exceptions.ResourceNotFoundException;
import Warehousemanagement.project.security.config.CustomUserDetails;
import Warehousemanagement.project.security.config.JwtTokenProvider;
import Warehousemanagement.project.security.dto.request.LoginRequest;
import Warehousemanagement.project.security.dto.response.AuthResponse;
import Warehousemanagement.project.security.model.Role;
import Warehousemanagement.project.security.model.User;
import Warehousemanagement.project.security.repository.UserRepository;
import Warehousemanagement.project.security.service.AuthService;
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

package Warehousemanagement.project.controller;

import Warehousemanagement.project.config.CustomUserDetails;
import Warehousemanagement.project.dto.request.CreateUserRequest;
import Warehousemanagement.project.dto.request.UpdateUserRequest;
import Warehousemanagement.project.dto.response.ApiResponse;
import Warehousemanagement.project.dto.response.PagedResponse;
import Warehousemanagement.project.dto.response.UserDetailResponse;
import Warehousemanagement.project.dto.response.UserSummaryResponse;
import Warehousemanagement.project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public ResponseEntity<ApiResponse<UserDetailResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDetailResponse response = userService.createUser(request);
        return new ResponseEntity<>(ApiResponse.success("User created successfully", response), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public ResponseEntity<ApiResponse<UserDetailResponse>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request,
            @AuthenticationPrincipal CustomUserDetails caller) {
        Long callerWarehouseId = isSuperAdmin(caller) ? null : caller.getWarehouseId();
        UserDetailResponse response = userService.updateUser(id, request, callerWarehouseId);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public ResponseEntity<ApiResponse<UserDetailResponse>> getUserById(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails caller) {
        Long callerWarehouseId = isSuperAdmin(caller) ? null : caller.getWarehouseId();
        UserDetailResponse response = userService.getUserById(id, callerWarehouseId);
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", response));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public ResponseEntity<ApiResponse<PagedResponse<UserSummaryResponse>>> getAllUsers(
            @RequestParam(required = false) Long warehouseId,
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "fullName") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @AuthenticationPrincipal CustomUserDetails caller) {

        Long effectiveWarehouseId = isSuperAdmin(caller) && warehouseId != null ? warehouseId : caller.getWarehouseId();
        int boundedSize = Math.min(Math.max(size, 1), 100);
        Sort.Direction direction = "DESC".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, boundedSize, Sort.by(direction, sortBy));

        PagedResponse<UserSummaryResponse> response = userService.getAllUsersInWarehouse(effectiveWarehouseId, query, pageable);
        return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", response));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public ResponseEntity<ApiResponse<Void>> setUserActiveStatus(
            @PathVariable Long id,
            @RequestParam boolean isActive,
            @AuthenticationPrincipal CustomUserDetails caller) {
        Long callerWarehouseId = isSuperAdmin(caller) ? null : caller.getWarehouseId();
        userService.setUserActiveStatus(id, isActive, callerWarehouseId);
        String msg = isActive ? "User activated successfully" : "User deactivated successfully";
        return ResponseEntity.ok(ApiResponse.success(msg, null));
    }

    private boolean isSuperAdmin(CustomUserDetails caller) {
        return caller.getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
    }
}

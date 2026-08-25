package Warehousemanagement.project.security.controller;

import Warehousemanagement.project.common.dto.ApiResponse;
import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.security.dto.request.CreateRoleRequest;
import Warehousemanagement.project.security.dto.request.UpdateRolePermissionsRequest;
import Warehousemanagement.project.security.dto.request.UpdateRoleRequest;
import Warehousemanagement.project.security.dto.response.RoleDetailResponse;
import Warehousemanagement.project.security.dto.response.RoleSummaryResponse;
import Warehousemanagement.project.security.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
@Tag(name = "Roles", description = "Endpoints for dynamic role creation, permission binding, and role lifecycle management")
@SecurityRequirement(name = "BearerAuth")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
    @Operation(summary = "Create dynamic role", description = "Creates a new custom role and associates an initial set of permission IDs.")
    public ResponseEntity<ApiResponse<RoleDetailResponse>> createRole(@Valid @RequestBody CreateRoleRequest request) {
        RoleDetailResponse response = roleService.createRole(request);
        return new ResponseEntity<>(ApiResponse.success("Role created successfully", response), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
    @Operation(summary = "Update role metadata", description = "Modifies role description.")
    public ResponseEntity<ApiResponse<RoleDetailResponse>> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRoleRequest request) {
        RoleDetailResponse response = roleService.updateRole(id, request);
        return ResponseEntity.ok(ApiResponse.success("Role updated successfully", response));
    }

    @PutMapping("/{id}/permissions")
    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
    @Operation(summary = "Update role permissions", description = "Dynamically binds or modifies the set of permissions assigned to this role.")
    public ResponseEntity<ApiResponse<RoleDetailResponse>> updateRolePermissions(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRolePermissionsRequest request) {
        RoleDetailResponse response = roleService.updateRolePermissions(id, request);
        return ResponseEntity.ok(ApiResponse.success("Role permissions updated successfully", response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE', 'USER_MANAGE')")
    @Operation(summary = "Get role details", description = "Retrieves role details with active permissions and assigned user count.")
    public ResponseEntity<ApiResponse<RoleDetailResponse>> getRoleById(@PathVariable Long id) {
        RoleDetailResponse response = roleService.getRoleById(id);
        return ResponseEntity.ok(ApiResponse.success("Role retrieved successfully", response));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE', 'USER_MANAGE')")
    @Operation(summary = "List roles paginated", description = "Retrieves a paginated list of system and dynamic custom roles.")
    public ResponseEntity<ApiResponse<PagedResponse<RoleSummaryResponse>>> getAllRoles(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        int boundedSize = Math.min(Math.max(size, 1), 100);
        Sort.Direction direction = "DESC".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, boundedSize, Sort.by(direction, sortBy));

        PagedResponse<RoleSummaryResponse> response = roleService.getAllRoles(query, pageable);
        return ResponseEntity.ok(ApiResponse.success("Roles retrieved successfully", response));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
    @Operation(summary = "Delete role", description = "Deletes a custom dynamic role. Core system roles and roles with active assigned users cannot be deleted.")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok(ApiResponse.success("Role deleted successfully", null));
    }
}

package Warehousemanagement.project.security.controller;

import Warehousemanagement.project.common.dto.ApiResponse;
import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.security.dto.request.CreateRoleRequest;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Roles", description = "Endpoints for dynamic RBAC role authoring and permission attachments")
@SecurityRequirement(name = "BearerAuth")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
    @Operation(summary = "Create custom role", description = "Creates a new custom role with associated granular action permissions.")
    public ResponseEntity<ApiResponse<RoleDetailResponse>> createRole(@Valid @RequestBody CreateRoleRequest request) {
        RoleDetailResponse response = roleService.createRole(request);
        return new ResponseEntity<>(ApiResponse.success("Role created successfully", response), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
    @Operation(summary = "Update role permissions", description = "Updates role description and permission set.")
    public ResponseEntity<ApiResponse<RoleDetailResponse>> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRoleRequest request) {
        RoleDetailResponse response = roleService.updateRole(id, request);
        return ResponseEntity.ok(ApiResponse.success("Role updated successfully", response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
    @Operation(summary = "Get role by ID", description = "Retrieves role details and attached permission list.")
    public ResponseEntity<ApiResponse<RoleDetailResponse>> getRoleById(@PathVariable Long id) {
        RoleDetailResponse response = roleService.getRoleById(id);
        return ResponseEntity.ok(ApiResponse.success("Role retrieved successfully", response));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
    @Operation(summary = "List roles paginated", description = "Retrieves a paginated list of system and custom roles.")
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
    @Operation(summary = "Delete custom role", description = "Deletes a custom role. System protected roles cannot be deleted.")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok(ApiResponse.success("Role deleted successfully", null));
    }
}

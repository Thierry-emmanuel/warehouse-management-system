package Warehousemanagement.project.security.controller;

import Warehousemanagement.project.common.dto.ApiResponse;
import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.security.dto.request.CreatePermissionRequest;
import Warehousemanagement.project.security.dto.request.UpdatePermissionRequest;
import Warehousemanagement.project.security.dto.response.PermissionDetailResponse;
import Warehousemanagement.project.security.dto.response.PermissionSummaryResponse;
import Warehousemanagement.project.security.enums.PermissionCategory;
import Warehousemanagement.project.security.service.PermissionService;
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
@RequestMapping("/api/v1/permissions")
@Tag(name = "Permissions", description = "Endpoints for dynamic permission creation, modification, inspection, and lifecycle management")
@SecurityRequirement(name = "BearerAuth")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PERMISSION_MANAGE')")
    @Operation(summary = "Create dynamic permission", description = "Creates a new custom permission and registers it within the domain hierarchy.")
    public ResponseEntity<ApiResponse<PermissionDetailResponse>> createPermission(@Valid @RequestBody CreatePermissionRequest request) {
        PermissionDetailResponse response = permissionService.createPermission(request);
        return new ResponseEntity<>(ApiResponse.success("Permission created successfully", response), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_MANAGE')")
    @Operation(summary = "Update permission metadata", description = "Modifies permission description and category.")
    public ResponseEntity<ApiResponse<PermissionDetailResponse>> updatePermission(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePermissionRequest request) {
        PermissionDetailResponse response = permissionService.updatePermission(id, request);
        return ResponseEntity.ok(ApiResponse.success("Permission updated successfully", response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('PERMISSION_MANAGE', 'ROLE_MANAGE')")
    @Operation(summary = "Get permission details", description = "Retrieves permission metadata, audit timestamps, and assigned role names.")
    public ResponseEntity<ApiResponse<PermissionDetailResponse>> getPermissionById(@PathVariable Long id) {
        PermissionDetailResponse response = permissionService.getPermissionById(id);
        return ResponseEntity.ok(ApiResponse.success("Permission retrieved successfully", response));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('PERMISSION_MANAGE', 'ROLE_MANAGE')")
    @Operation(summary = "List permissions paginated", description = "Searches and filters permissions by query text or category with bounded pagination.")
    public ResponseEntity<ApiResponse<PagedResponse<PermissionSummaryResponse>>> getAllPermissions(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) PermissionCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        int boundedSize = Math.min(Math.max(size, 1), 100);
        Sort.Direction direction = "DESC".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, boundedSize, Sort.by(direction, sortBy));

        PagedResponse<PermissionSummaryResponse> response = permissionService.getAllPermissions(query, category, pageable);
        return ResponseEntity.ok(ApiResponse.success("Permissions retrieved successfully", response));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_MANAGE')")
    @Operation(summary = "Delete permission", description = "Deletes a custom permission and disassociates it from roles. System-defined permissions are protected.")
    public ResponseEntity<ApiResponse<Void>> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.ok(ApiResponse.success("Permission deleted successfully", null));
    }
}

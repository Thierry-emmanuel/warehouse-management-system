package Warehousemanagement.project.security.controller;

import Warehousemanagement.project.common.dto.ApiResponse;
import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.security.dto.response.PermissionResponse;
import Warehousemanagement.project.security.enums.PermissionCategory;
import Warehousemanagement.project.security.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/permissions")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Permissions", description = "Endpoints for granular privilege catalog exploration")
@SecurityRequirement(name = "BearerAuth")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PERMISSION_MANAGE')")
    @Operation(summary = "List permissions paginated", description = "Retrieves all available fine-grained action permissions.")
    public ResponseEntity<ApiResponse<PagedResponse<PermissionResponse>>> getAllPermissions(
            @RequestParam(required = false) PermissionCategory category,
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "category") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        int boundedSize = Math.min(Math.max(size, 1), 100);
        Sort.Direction direction = "DESC".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, boundedSize, Sort.by(direction, sortBy));

        PagedResponse<PermissionResponse> response = permissionService.getAllPermissions(category, query, pageable);
        return ResponseEntity.ok(ApiResponse.success("Permissions retrieved successfully", response));
    }
}

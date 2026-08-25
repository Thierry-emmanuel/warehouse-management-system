package Warehousemanagement.project.warehouse.controller;

import Warehousemanagement.project.common.dto.ApiResponse;
import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.warehouse.dto.response.LocationResponse;
import Warehousemanagement.project.warehouse.service.LocationService;
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
@RequestMapping("/api/v1/locations")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Locations", description = "Endpoints for physical spatial hierarchy and bin coordinates")
@SecurityRequirement(name = "BearerAuth")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('INVENTORY_READ', 'INVENTORY_WRITE')")
    @Operation(summary = "List locations paginated", description = "Retrieves storage bins and dock locations.")
    public ResponseEntity<ApiResponse<PagedResponse<LocationResponse>>> getLocations(
            @RequestParam(required = false) Long warehouseId,
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "code") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        int boundedSize = Math.min(Math.max(size, 1), 100);
        Sort.Direction direction = "DESC".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, boundedSize, Sort.by(direction, sortBy));

        PagedResponse<LocationResponse> response = locationService.getLocations(warehouseId, query, pageable);
        return ResponseEntity.ok(ApiResponse.success("Locations retrieved successfully", response));
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasAnyAuthority('INVENTORY_READ', 'INVENTORY_WRITE')")
    @Operation(summary = "Get location by code", description = "Retrieves location details by bin code (e.g. WH1-Z01-A01-S1-B01).")
    public ResponseEntity<ApiResponse<LocationResponse>> getLocationByCode(@PathVariable String code) {
        LocationResponse response = locationService.getLocationByCode(code);
        return ResponseEntity.ok(ApiResponse.success("Location retrieved successfully", response));
    }
}

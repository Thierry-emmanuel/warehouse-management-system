package Warehousemanagement.project.inventory.controller;

import Warehousemanagement.project.common.dto.ApiResponse;
import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.inventory.dto.response.InventoryItemResponse;
import Warehousemanagement.project.inventory.dto.response.StockMovementResponse;
import Warehousemanagement.project.inventory.service.InventoryService;
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
@RequestMapping("/api/v1/inventory")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Inventory", description = "Endpoints for physical stock balances, batch status, and audit ledgers")
@SecurityRequirement(name = "BearerAuth")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('INVENTORY_READ', 'INVENTORY_WRITE')")
    @Operation(summary = "List inventory balances paginated", description = "Retrieves live stock levels across warehouse bins.")
    public ResponseEntity<ApiResponse<PagedResponse<InventoryItemResponse>>> getInventoryBalances(
            @RequestParam(required = false) Long warehouseId,
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        int boundedSize = Math.min(Math.max(size, 1), 100);
        Sort.Direction direction = "DESC".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, boundedSize, Sort.by(direction, sortBy));

        PagedResponse<InventoryItemResponse> response = inventoryService.getInventoryBalances(warehouseId, query, pageable);
        return ResponseEntity.ok(ApiResponse.success("Inventory balances retrieved successfully", response));
    }

    @GetMapping("/movements")
    @PreAuthorize("hasAnyAuthority('INVENTORY_READ', 'INVENTORY_WRITE')")
    @Operation(summary = "List stock audit movements", description = "Retrieves append-only ledger of physical stock movements.")
    public ResponseEntity<ApiResponse<PagedResponse<StockMovementResponse>>> getStockMovements(
            @RequestParam(required = false) Long warehouseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        int boundedSize = Math.min(Math.max(size, 1), 100);
        Pageable pageable = PageRequest.of(page, boundedSize);

        PagedResponse<StockMovementResponse> response = inventoryService.getStockMovements(warehouseId, pageable);
        return ResponseEntity.ok(ApiResponse.success("Stock movements retrieved successfully", response));
    }
}

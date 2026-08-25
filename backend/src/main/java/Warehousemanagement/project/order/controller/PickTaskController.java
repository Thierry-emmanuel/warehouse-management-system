package Warehousemanagement.project.order.controller;

import Warehousemanagement.project.common.dto.ApiResponse;
import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.order.dto.response.PickTaskResponse;
import Warehousemanagement.project.order.service.PickTaskService;
import Warehousemanagement.project.security.config.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pick-tasks")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Pick Tasks", description = "Endpoints for directed wave picking and handheld scanner execution")
@SecurityRequirement(name = "BearerAuth")
public class PickTaskController {

    private final PickTaskService pickTaskService;

    public PickTaskController(PickTaskService pickTaskService) {
        this.pickTaskService = pickTaskService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('OPERATIONS_EXECUTE', 'INVENTORY_READ')")
    @Operation(summary = "List pick tasks paginated", description = "Retrieves wave pick tasks assigned to the caller or warehouse.")
    public ResponseEntity<ApiResponse<PagedResponse<PickTaskResponse>>> getPickTasks(
            @RequestParam(required = false) Long warehouseId,
            @RequestParam(required = false) String operator,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @AuthenticationPrincipal CustomUserDetails caller) {

        int boundedSize = Math.min(Math.max(size, 1), 100);
        Pageable pageable = PageRequest.of(page, boundedSize, Sort.by(Sort.Direction.ASC, "sequenceOrder"));
        String effectiveOperator = operator != null ? operator : (caller != null ? caller.getUsername() : null);

        PagedResponse<PickTaskResponse> response = pickTaskService.getPickTasks(warehouseId, effectiveOperator, pageable);
        return ResponseEntity.ok(ApiResponse.success("Pick tasks retrieved successfully", response));
    }

    @PostMapping("/{id}/confirm")
    @PreAuthorize("hasAnyAuthority('OPERATIONS_EXECUTE', 'INVENTORY_WRITE')")
    @Operation(summary = "Confirm pick quantity", description = "Records picked units from a bin location via barcode scan.")
    public ResponseEntity<ApiResponse<PickTaskResponse>> confirmPick(
            @PathVariable Long id,
            @RequestParam Integer quantity,
            @AuthenticationPrincipal CustomUserDetails caller) {

        String operator = caller != null ? caller.getUsername() : "system";
        PickTaskResponse response = pickTaskService.confirmPick(id, quantity, operator);
        return ResponseEntity.ok(ApiResponse.success("Pick task confirmed", response));
    }
}

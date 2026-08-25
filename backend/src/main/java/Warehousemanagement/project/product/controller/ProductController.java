package Warehousemanagement.project.product.controller;

import Warehousemanagement.project.common.dto.ApiResponse;
import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.product.dto.request.CreateProductRequest;
import Warehousemanagement.project.product.dto.response.ProductResponse;
import Warehousemanagement.project.product.service.ProductService;
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
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Products", description = "Endpoints for master SKU catalog, barcodes, and stock thresholds")
@SecurityRequirement(name = "BearerAuth")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('INVENTORY_WRITE')")
    @Operation(summary = "Create product master SKU", description = "Provisions a new master SKU with category mapping and reorder levels.")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody CreateProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return new ResponseEntity<>(ApiResponse.success("Product created successfully", response), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('INVENTORY_READ', 'INVENTORY_WRITE')")
    @Operation(summary = "List products paginated", description = "Retrieves master SKU catalog with search and category filters.")
    public ResponseEntity<ApiResponse<PagedResponse<ProductResponse>>> getProducts(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "sku") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        int boundedSize = Math.min(Math.max(size, 1), 100);
        Sort.Direction direction = "DESC".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, boundedSize, Sort.by(direction, sortBy));

        PagedResponse<ProductResponse> response = productService.getProducts(query, categoryId, pageable);
        return ResponseEntity.ok(ApiResponse.success("Products retrieved successfully", response));
    }

    @GetMapping("/{sku}")
    @PreAuthorize("hasAnyAuthority('INVENTORY_READ', 'INVENTORY_WRITE')")
    @Operation(summary = "Get product by SKU", description = "Retrieves product master details and physical specifications.")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductBySku(@PathVariable String sku) {
        ProductResponse response = productService.getProductBySku(sku);
        return ResponseEntity.ok(ApiResponse.success("Product retrieved successfully", response));
    }
}

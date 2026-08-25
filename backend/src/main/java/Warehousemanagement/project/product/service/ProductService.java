package Warehousemanagement.project.product.service;

import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.product.dto.request.CreateProductRequest;
import Warehousemanagement.project.product.dto.response.ProductResponse;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request);
    PagedResponse<ProductResponse> getProducts(String query, Long categoryId, Pageable pageable);
    ProductResponse getProductBySku(String sku);
}

package Warehousemanagement.project.product.service.impl;

import Warehousemanagement.project.category.model.Category;
import Warehousemanagement.project.category.repository.CategoryRepository;
import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.common.exceptions.BadRequestException;
import Warehousemanagement.project.common.exceptions.ResourceNotFoundException;
import Warehousemanagement.project.product.dto.request.CreateProductRequest;
import Warehousemanagement.project.product.dto.response.ProductResponse;
import Warehousemanagement.project.product.model.Product;
import Warehousemanagement.project.product.repository.ProductRepository;
import Warehousemanagement.project.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        if (productRepository.existsBySku(request.getSku().trim())) {
            throw new BadRequestException("Product with SKU '" + request.getSku() + "' already exists.");
        }
        if (productRepository.existsByBarcode(request.getBarcode().trim())) {
            throw new BadRequestException("Product with Barcode '" + request.getBarcode() + "' already exists.");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));

        Product product = new Product(
            request.getSku().trim().toUpperCase(),
            request.getName().trim(),
            request.getBarcode().trim(),
            category,
            request.getUnitOfMeasure(),
            request.getUnitPrice()
        );
        product.setDescription(request.getDescription());
        product.setWeightKg(request.getWeightKg());
        product.setVolumeCbm(request.getVolumeCbm());
        product.setMinReorderLevel(request.getMinReorderLevel());
        product.setMaxStockLevel(request.getMaxStockLevel());
        product.setSafetyStock(request.getSafetyStock());

        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<ProductResponse> getProducts(String query, Long categoryId, Pageable pageable) {
        Page<Product> page = (query != null && !query.isBlank())
            ? productRepository.findByQuery(query.trim(), pageable)
            : productRepository.findAll(pageable);

        List<ProductResponse> dtos = page.getContent().stream().map(this::toResponse).toList();
        return new PagedResponse<>(dtos, page.getNumber(), page.getSize(), page.getTotalElements(),
                page.getTotalPages(), page.isFirst(), page.isLast(), page.hasNext(), page.hasPrevious());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductBySku(String sku) {
        Product p = productRepository.findBySku(sku)
            .orElseThrow(() -> new ResourceNotFoundException("Product", "sku", sku));
        return toResponse(p);
    }

    private ProductResponse toResponse(Product p) {
        return new ProductResponse(
            p.getId(),
            p.getSku(),
            p.getName(),
            p.getDescription(),
            p.getBarcode(),
            p.getCategory() != null ? p.getCategory().getId() : null,
            p.getCategory() != null ? p.getCategory().getName() : null,
            p.getUnitOfMeasure() != null ? p.getUnitOfMeasure().name() : null,
            p.getUnitPrice(),
            p.getWeightKg(),
            p.getVolumeCbm(),
            p.getMinReorderLevel(),
            p.getMaxStockLevel(),
            p.getSafetyStock(),
            p.isActive()
        );
    }
}

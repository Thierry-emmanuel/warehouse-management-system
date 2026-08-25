package Warehousemanagement.project.product.dto.request;

import Warehousemanagement.project.product.enums.UnitOfMeasure;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CreateProductRequest {

    @NotBlank(message = "SKU code is required")
    @Size(min = 3, max = 50, message = "SKU must be between 3 and 50 characters")
    private String sku;

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 150, message = "Name must be between 2 and 150 characters")
    private String name;

    private String description;

    @NotBlank(message = "Barcode is required")
    @Size(min = 3, max = 100, message = "Barcode must be between 3 and 100 characters")
    private String barcode;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Unit of measure is required")
    private UnitOfMeasure unitOfMeasure = UnitOfMeasure.PCS;

    @NotNull(message = "Unit price is required")
    @Positive(message = "Unit price must be positive")
    private BigDecimal unitPrice;

    private Double weightKg;
    private Double volumeCbm;
    private Integer minReorderLevel = 10;
    private Integer maxStockLevel = 500;
    private Integer safetyStock = 5;

    public CreateProductRequest() {}

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public UnitOfMeasure getUnitOfMeasure() { return unitOfMeasure; }
    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) { this.unitOfMeasure = unitOfMeasure; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }
    public Double getVolumeCbm() { return volumeCbm; }
    public void setVolumeCbm(Double volumeCbm) { this.volumeCbm = volumeCbm; }
    public Integer getMinReorderLevel() { return minReorderLevel; }
    public void setMinReorderLevel(Integer minReorderLevel) { this.minReorderLevel = minReorderLevel; }
    public Integer getMaxStockLevel() { return maxStockLevel; }
    public void setMaxStockLevel(Integer maxStockLevel) { this.maxStockLevel = maxStockLevel; }
    public Integer getSafetyStock() { return safetyStock; }
    public void setSafetyStock(Integer safetyStock) { this.safetyStock = safetyStock; }
}

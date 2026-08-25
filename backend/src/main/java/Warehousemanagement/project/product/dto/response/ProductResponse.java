package Warehousemanagement.project.product.dto.response;

import java.math.BigDecimal;

public class ProductResponse {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private String barcode;
    private Long categoryId;
    private String categoryName;
    private String unitOfMeasure;
    private BigDecimal unitPrice;
    private Double weightKg;
    private Double volumeCbm;
    private Integer minReorderLevel;
    private Integer maxStockLevel;
    private Integer safetyStock;
    private boolean isActive;

    public ProductResponse() {}

    public ProductResponse(Long id, String sku, String name, String description, String barcode,
                           Long categoryId, String categoryName, String unitOfMeasure, BigDecimal unitPrice,
                           Double weightKg, Double volumeCbm, Integer minReorderLevel, Integer maxStockLevel,
                           Integer safetyStock, boolean isActive) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.unitOfMeasure = unitOfMeasure;
        this.unitPrice = unitPrice;
        this.weightKg = weightKg;
        this.volumeCbm = volumeCbm;
        this.minReorderLevel = minReorderLevel;
        this.maxStockLevel = maxStockLevel;
        this.safetyStock = safetyStock;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getBarcode() { return barcode; }
    public Long getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }
    public String getUnitOfMeasure() { return unitOfMeasure; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public Double getWeightKg() { return weightKg; }
    public Double getVolumeCbm() { return volumeCbm; }
    public Integer getMinReorderLevel() { return minReorderLevel; }
    public Integer getMaxStockLevel() { return maxStockLevel; }
    public Integer getSafetyStock() { return safetyStock; }
    public boolean isActive() { return isActive; }
}

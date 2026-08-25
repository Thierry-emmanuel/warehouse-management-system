package Warehousemanagement.project.inventory.dto.response;

public class InventoryItemResponse {
    private Long id;
    private Long productId;
    private String productSku;
    private String productName;
    private String barcode;
    private String unitOfMeasure;
    private Long locationId;
    private String locationCode;
    private String batchNumber;
    private String expirationDate;
    private Integer quantityOnHand;
    private Integer quantityAllocated;
    private Integer quantityAvailable;
    private Long version;

    public InventoryItemResponse() {}

    public InventoryItemResponse(Long id, Long productId, String productSku, String productName, String barcode,
                                 String unitOfMeasure, Long locationId, String locationCode, String batchNumber,
                                 String expirationDate, Integer quantityOnHand, Integer quantityAllocated,
                                 Integer quantityAvailable, Long version) {
        this.id = id;
        this.productId = productId;
        this.productSku = productSku;
        this.productName = productName;
        this.barcode = barcode;
        this.unitOfMeasure = unitOfMeasure;
        this.locationId = locationId;
        this.locationCode = locationCode;
        this.batchNumber = batchNumber;
        this.expirationDate = expirationDate;
        this.quantityOnHand = quantityOnHand;
        this.quantityAllocated = quantityAllocated;
        this.quantityAvailable = quantityAvailable;
        this.version = version;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getProductSku() { return productSku; }
    public void setProductSku(String productSku) { this.productSku = productSku; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public String getUnitOfMeasure() { return unitOfMeasure; }
    public void setUnitOfMeasure(String unitOfMeasure) { this.unitOfMeasure = unitOfMeasure; }
    public Long getLocationId() { return locationId; }
    public void setLocationId(Long locationId) { this.locationId = locationId; }
    public String getLocationCode() { return locationCode; }
    public void setLocationCode(String locationCode) { this.locationCode = locationCode; }
    public String getBatchNumber() { return batchNumber; }
    public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }
    public String getExpirationDate() { return expirationDate; }
    public void setExpirationDate(String expirationDate) { this.expirationDate = expirationDate; }
    public Integer getQuantityOnHand() { return quantityOnHand; }
    public void setQuantityOnHand(Integer quantityOnHand) { this.quantityOnHand = quantityOnHand; }
    public Integer getQuantityAllocated() { return quantityAllocated; }
    public void setQuantityAllocated(Integer quantityAllocated) { this.quantityAllocated = quantityAllocated; }
    public Integer getQuantityAvailable() { return quantityAvailable; }
    public void setQuantityAvailable(Integer quantityAvailable) { this.quantityAvailable = quantityAvailable; }
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
}

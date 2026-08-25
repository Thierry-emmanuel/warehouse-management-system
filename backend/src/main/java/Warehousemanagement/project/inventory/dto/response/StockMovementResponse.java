package Warehousemanagement.project.inventory.dto.response;

import java.time.LocalDateTime;

public class StockMovementResponse {
    private Long id;
    private Long warehouseId;
    private String productSku;
    private String productName;
    private String batchNumber;
    private String sourceLocationCode;
    private String destinationLocationCode;
    private Integer quantity;
    private String movementType;
    private String referenceType;
    private String referenceId;
    private String operatorUsername;
    private LocalDateTime timestamp;

    public StockMovementResponse() {}

    public StockMovementResponse(Long id, Long warehouseId, String productSku, String productName,
                                 String batchNumber, String sourceLocationCode, String destinationLocationCode,
                                 Integer quantity, String movementType, String referenceType,
                                 String referenceId, String operatorUsername, LocalDateTime timestamp) {
        this.id = id;
        this.warehouseId = warehouseId;
        this.productSku = productSku;
        this.productName = productName;
        this.batchNumber = batchNumber;
        this.sourceLocationCode = sourceLocationCode;
        this.destinationLocationCode = destinationLocationCode;
        this.quantity = quantity;
        this.movementType = movementType;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.operatorUsername = operatorUsername;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public Long getWarehouseId() { return warehouseId; }
    public String getProductSku() { return productSku; }
    public String getProductName() { return productName; }
    public String getBatchNumber() { return batchNumber; }
    public String getSourceLocationCode() { return sourceLocationCode; }
    public String getDestinationLocationCode() { return destinationLocationCode; }
    public Integer getQuantity() { return quantity; }
    public String getMovementType() { return movementType; }
    public String getReferenceType() { return referenceType; }
    public String getReferenceId() { return referenceId; }
    public String getOperatorUsername() { return operatorUsername; }
    public LocalDateTime getTimestamp() { return timestamp; }
}

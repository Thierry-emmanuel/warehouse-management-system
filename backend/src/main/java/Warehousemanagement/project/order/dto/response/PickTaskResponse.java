package Warehousemanagement.project.order.dto.response;

import Warehousemanagement.project.order.enums.PickTaskStatus;

public class PickTaskResponse {
    private Long id;
    private String taskCode;
    private String orderNumber;
    private String sku;
    private String productName;
    private String barcode;
    private String binLocation;
    private Integer targetQuantity;
    private Integer pickedQuantity;
    private PickTaskStatus status;
    private String assignedOperator;
    private Integer sequenceOrder;

    public PickTaskResponse() {}

    public PickTaskResponse(Long id, String taskCode, String orderNumber, String sku, String productName,
                            String barcode, String binLocation, Integer targetQuantity, Integer pickedQuantity,
                            PickTaskStatus status, String assignedOperator, Integer sequenceOrder) {
        this.id = id;
        this.taskCode = taskCode;
        this.orderNumber = orderNumber;
        this.sku = sku;
        this.productName = productName;
        this.barcode = barcode;
        this.binLocation = binLocation;
        this.targetQuantity = targetQuantity;
        this.pickedQuantity = pickedQuantity;
        this.status = status;
        this.assignedOperator = assignedOperator;
        this.sequenceOrder = sequenceOrder;
    }

    public Long getId() { return id; }
    public String getTaskCode() { return taskCode; }
    public String getOrderNumber() { return orderNumber; }
    public String getSku() { return sku; }
    public String getProductName() { return productName; }
    public String getBarcode() { return barcode; }
    public String getBinLocation() { return binLocation; }
    public Integer getTargetQuantity() { return targetQuantity; }
    public Integer getPickedQuantity() { return pickedQuantity; }
    public PickTaskStatus getStatus() { return status; }
    public String getAssignedOperator() { return assignedOperator; }
    public Integer getSequenceOrder() { return sequenceOrder; }
}

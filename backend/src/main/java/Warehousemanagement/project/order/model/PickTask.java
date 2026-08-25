package Warehousemanagement.project.order.model;

import Warehousemanagement.project.order.enums.PickTaskStatus;
import Warehousemanagement.project.product.model.Product;
import Warehousemanagement.project.warehouse.model.Location;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pick_tasks", indexes = {
    @Index(name = "idx_pt_code", columnList = "task_code", unique = true),
    @Index(name = "idx_pt_operator_status", columnList = "assigned_operator_username, status")
})
public class PickTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_code", nullable = false, unique = true, length = 50)
    private String taskCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sales_order_id", nullable = false)
    private SalesOrder salesOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_location_id", nullable = false)
    private Location sourceLocation;

    @Column(name = "assigned_operator_username", length = 50)
    private String assignedOperatorUsername;

    @Column(name = "target_quantity", nullable = false)
    private Integer targetQuantity;

    @Column(name = "picked_quantity", nullable = false)
    private Integer pickedQuantity = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private PickTaskStatus status = PickTaskStatus.PENDING;

    @Column(name = "tote_barcode", length = 50)
    private String toteBarcode;

    @Column(name = "sequence_order", nullable = false)
    private Integer sequenceOrder = 1;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    public PickTask() {}

    public PickTask(String taskCode, SalesOrder salesOrder, Product product, Location sourceLocation,
                    Integer targetQuantity, String assignedOperatorUsername) {
        this.taskCode = taskCode;
        this.salesOrder = salesOrder;
        this.product = product;
        this.sourceLocation = sourceLocation;
        this.targetQuantity = targetQuantity;
        this.assignedOperatorUsername = assignedOperatorUsername;
        this.status = PickTaskStatus.PENDING;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTaskCode() { return taskCode; }
    public void setTaskCode(String taskCode) { this.taskCode = taskCode; }

    public SalesOrder getSalesOrder() { return salesOrder; }
    public void setSalesOrder(SalesOrder salesOrder) { this.salesOrder = salesOrder; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Location getSourceLocation() { return sourceLocation; }
    public void setSourceLocation(Location sourceLocation) { this.sourceLocation = sourceLocation; }

    public String getAssignedOperatorUsername() { return assignedOperatorUsername; }
    public void setAssignedOperatorUsername(String assignedOperatorUsername) { this.assignedOperatorUsername = assignedOperatorUsername; }

    public Integer getTargetQuantity() { return targetQuantity; }
    public void setTargetQuantity(Integer targetQuantity) { this.targetQuantity = targetQuantity; }

    public Integer getPickedQuantity() { return pickedQuantity; }
    public void setPickedQuantity(Integer pickedQuantity) { this.pickedQuantity = pickedQuantity; }

    public PickTaskStatus getStatus() { return status; }
    public void setStatus(PickTaskStatus status) { this.status = status; }

    public String getToteBarcode() { return toteBarcode; }
    public void setToteBarcode(String toteBarcode) { this.toteBarcode = toteBarcode; }

    public Integer getSequenceOrder() { return sequenceOrder; }
    public void setSequenceOrder(Integer sequenceOrder) { this.sequenceOrder = sequenceOrder; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}

package Warehousemanagement.project.inventory.model;

import Warehousemanagement.project.inventory.enums.MovementType;
import Warehousemanagement.project.product.model.Batch;
import Warehousemanagement.project.product.model.Product;
import Warehousemanagement.project.warehouse.model.Location;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements", indexes = {
    @Index(name = "idx_sm_product", columnList = "product_id"),
    @Index(name = "idx_sm_warehouse_time", columnList = "warehouse_id, timestamp"),
    @Index(name = "idx_sm_ref", columnList = "reference_type, reference_id")
})
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "warehouse_id", nullable = false)
    private Long warehouseId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_location_id")
    private Location sourceLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_location_id")
    private Location destinationLocation;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false, length = 40)
    private MovementType movementType;

    @Column(name = "reference_type", length = 50)
    private String referenceType;

    @Column(name = "reference_id", length = 50)
    private String referenceId;

    @Column(name = "operator_username", nullable = false, length = 50)
    private String operatorUsername;

    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    public StockMovement() {}

    public StockMovement(Long warehouseId, Product product, Batch batch, Location sourceLocation,
                         Location destinationLocation, Integer quantity, MovementType movementType,
                         String referenceType, String referenceId, String operatorUsername) {
        this.warehouseId = warehouseId;
        this.product = product;
        this.batch = batch;
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
        this.quantity = quantity;
        this.movementType = movementType;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.operatorUsername = operatorUsername;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Batch getBatch() { return batch; }
    public void setBatch(Batch batch) { this.batch = batch; }

    public Location getSourceLocation() { return sourceLocation; }
    public void setSourceLocation(Location sourceLocation) { this.sourceLocation = sourceLocation; }

    public Location getDestinationLocation() { return destinationLocation; }
    public void setDestinationLocation(Location destinationLocation) { this.destinationLocation = destinationLocation; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public MovementType getMovementType() { return movementType; }
    public void setMovementType(MovementType movementType) { this.movementType = movementType; }

    public String getReferenceType() { return referenceType; }
    public void setReferenceType(String referenceType) { this.referenceType = referenceType; }

    public String getReferenceId() { return referenceId; }
    public void setReferenceId(String referenceId) { this.referenceId = referenceId; }

    public String getOperatorUsername() { return operatorUsername; }
    public void setOperatorUsername(String operatorUsername) { this.operatorUsername = operatorUsername; }

    public LocalDateTime getTimestamp() { return timestamp; }
}

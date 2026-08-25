package Warehousemanagement.project.inventory.model;

import Warehousemanagement.project.inventory.enums.AdjustmentReason;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_adjustments", indexes = {
    @Index(name = "idx_sa_item", columnList = "inventory_item_id")
})
public class StockAdjustment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inventory_item_id", nullable = false)
    private InventoryItem inventoryItem;

    @Column(name = "quantity_change", nullable = false)
    private Integer quantityChange;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason", nullable = false, length = 40)
    private AdjustmentReason reason;

    @Column(name = "comments", length = 300)
    private String comments;

    @Column(name = "adjusted_by_username", nullable = false, length = 50)
    private String adjustedByUsername;

    @Column(name = "approved_by_username", length = 50)
    private String approvedByUsername;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public StockAdjustment() {}

    public StockAdjustment(InventoryItem inventoryItem, Integer quantityChange, AdjustmentReason reason,
                           String comments, String adjustedByUsername) {
        this.inventoryItem = inventoryItem;
        this.quantityChange = quantityChange;
        this.reason = reason;
        this.comments = comments;
        this.adjustedByUsername = adjustedByUsername;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public InventoryItem getInventoryItem() { return inventoryItem; }
    public void setInventoryItem(InventoryItem inventoryItem) { this.inventoryItem = inventoryItem; }

    public Integer getQuantityChange() { return quantityChange; }
    public void setQuantityChange(Integer quantityChange) { this.quantityChange = quantityChange; }

    public AdjustmentReason getReason() { return reason; }
    public void setReason(AdjustmentReason reason) { this.reason = reason; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public String getAdjustedByUsername() { return adjustedByUsername; }
    public void setAdjustedByUsername(String adjustedByUsername) { this.adjustedByUsername = adjustedByUsername; }

    public String getApprovedByUsername() { return approvedByUsername; }
    public void setApprovedByUsername(String approvedByUsername) { this.approvedByUsername = approvedByUsername; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}

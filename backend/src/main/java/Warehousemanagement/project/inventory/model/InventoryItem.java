package Warehousemanagement.project.inventory.model;

import Warehousemanagement.project.product.model.Batch;
import Warehousemanagement.project.product.model.Product;
import Warehousemanagement.project.warehouse.model.Location;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_items", indexes = {
    @Index(name = "idx_inv_prod_loc_batch", columnList = "product_id, location_id, batch_id", unique = true),
    @Index(name = "idx_inv_location", columnList = "location_id"),
    @Index(name = "idx_inv_product", columnList = "product_id")
})
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @Column(name = "quantity_on_hand", nullable = false)
    private Integer quantityOnHand = 0;

    @Column(name = "quantity_allocated", nullable = false)
    private Integer quantityAllocated = 0;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public InventoryItem() {}

    public InventoryItem(Product product, Location location, Batch batch, Integer quantityOnHand) {
        this.product = product;
        this.location = location;
        this.batch = batch;
        this.quantityOnHand = quantityOnHand;
        this.quantityAllocated = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Transient
    public Integer getQuantityAvailable() {
        return Math.max(0, this.quantityOnHand - this.quantityAllocated);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public Batch getBatch() { return batch; }
    public void setBatch(Batch batch) { this.batch = batch; }

    public Integer getQuantityOnHand() { return quantityOnHand; }
    public void setQuantityOnHand(Integer quantityOnHand) { this.quantityOnHand = quantityOnHand; }

    public Integer getQuantityAllocated() { return quantityAllocated; }
    public void setQuantityAllocated(Integer quantityAllocated) { this.quantityAllocated = quantityAllocated; }

    public Long getVersion() { return version; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}

package Warehousemanagement.project.warehouse.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "racks", indexes = {
    @Index(name = "idx_rack_aisle_code", columnList = "aisle_id, code", unique = true)
})
public class Rack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aisle_id", nullable = false)
    private Aisle aisle;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Column(name = "max_weight_kg")
    private Double maxWeightKg;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Rack() {}

    public Rack(Aisle aisle, String code, Double maxWeightKg) {
        this.aisle = aisle;
        this.code = code;
        this.maxWeightKg = maxWeightKg;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Aisle getAisle() { return aisle; }
    public void setAisle(Aisle aisle) { this.aisle = aisle; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Double getMaxWeightKg() { return maxWeightKg; }
    public void setMaxWeightKg(Double maxWeightKg) { this.maxWeightKg = maxWeightKg; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}

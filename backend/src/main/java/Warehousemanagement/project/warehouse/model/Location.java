package Warehousemanagement.project.warehouse.model;

import Warehousemanagement.project.warehouse.enums.LocationStatus;
import Warehousemanagement.project.warehouse.enums.LocationType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "locations", indexes = {
    @Index(name = "idx_location_code", columnList = "code", unique = true),
    @Index(name = "idx_location_wh_type", columnList = "warehouse_id, location_type")
})
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelf_id")
    private Shelf shelf;

    @Column(name = "code", nullable = false, unique = true, length = 60)
    private String code;

    @Column(name = "bin_number", length = 20)
    private String binNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "location_type", nullable = false, length = 30)
    private LocationType locationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private LocationStatus status = LocationStatus.ACTIVE;

    @Column(name = "max_weight_kg")
    private Double maxWeightKg;

    @Column(name = "max_volume_cbm")
    private Double maxVolumeCbm;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Location() {}

    public Location(Warehouse warehouse, Shelf shelf, String code, String binNumber, LocationType locationType) {
        this.warehouse = warehouse;
        this.shelf = shelf;
        this.code = code;
        this.binNumber = binNumber;
        this.locationType = locationType;
        this.status = LocationStatus.ACTIVE;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Warehouse getWarehouse() { return warehouse; }
    public void setWarehouse(Warehouse warehouse) { this.warehouse = warehouse; }

    public Shelf getShelf() { return shelf; }
    public void setShelf(Shelf shelf) { this.shelf = shelf; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getBinNumber() { return binNumber; }
    public void setBinNumber(String binNumber) { this.binNumber = binNumber; }

    public LocationType getLocationType() { return locationType; }
    public void setLocationType(LocationType locationType) { this.locationType = locationType; }

    public LocationStatus getStatus() { return status; }
    public void setStatus(LocationStatus status) { this.status = status; }

    public Double getMaxWeightKg() { return maxWeightKg; }
    public void setMaxWeightKg(Double maxWeightKg) { this.maxWeightKg = maxWeightKg; }

    public Double getMaxVolumeCbm() { return maxVolumeCbm; }
    public void setMaxVolumeCbm(Double maxVolumeCbm) { this.maxVolumeCbm = maxVolumeCbm; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}

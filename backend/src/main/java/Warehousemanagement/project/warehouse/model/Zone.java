package Warehousemanagement.project.warehouse.model;

import Warehousemanagement.project.warehouse.enums.ZoneType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "zones", indexes = {
    @Index(name = "idx_zone_warehouse_code", columnList = "warehouse_id, code", unique = true)
})
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(name = "code", nullable = false, length = 30)
    private String code;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "zone_type", nullable = false, length = 30)
    private ZoneType zoneType;

    @Column(name = "target_temperature_celsius")
    private Double targetTemperatureCelsius;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Zone() {}

    public Zone(Warehouse warehouse, String code, String name, ZoneType zoneType) {
        this.warehouse = warehouse;
        this.code = code;
        this.name = name;
        this.zoneType = zoneType;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Warehouse getWarehouse() { return warehouse; }
    public void setWarehouse(Warehouse warehouse) { this.warehouse = warehouse; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ZoneType getZoneType() { return zoneType; }
    public void setZoneType(ZoneType zoneType) { this.zoneType = zoneType; }

    public Double getTargetTemperatureCelsius() { return targetTemperatureCelsius; }
    public void setTargetTemperatureCelsius(Double targetTemperatureCelsius) { this.targetTemperatureCelsius = targetTemperatureCelsius; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}

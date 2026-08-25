package Warehousemanagement.project.order.model;

import Warehousemanagement.project.warehouse.model.Location;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipments", indexes = {
    @Index(name = "idx_shipment_number", columnList = "shipment_number", unique = true),
    @Index(name = "idx_shipment_so", columnList = "sales_order_id")
})
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shipment_number", nullable = false, unique = true, length = 50)
    private String shipmentNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sales_order_id", nullable = false)
    private SalesOrder salesOrder;

    @Column(name = "carrier_name", nullable = false, length = 100)
    private String carrierName;

    @Column(name = "tracking_number", length = 100)
    private String trackingNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_bay_location_id")
    private Location shippingBayLocation;

    @Column(name = "dispatched_by_username", nullable = false, length = 50)
    private String dispatchedByUsername;

    @Column(name = "dispatched_at", nullable = false, updatable = false)
    private LocalDateTime dispatchedAt = LocalDateTime.now();

    public Shipment() {}

    public Shipment(String shipmentNumber, SalesOrder salesOrder, String carrierName, String trackingNumber,
                    Location shippingBayLocation, String dispatchedByUsername) {
        this.shipmentNumber = shipmentNumber;
        this.salesOrder = salesOrder;
        this.carrierName = carrierName;
        this.trackingNumber = trackingNumber;
        this.shippingBayLocation = shippingBayLocation;
        this.dispatchedByUsername = dispatchedByUsername;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getShipmentNumber() { return shipmentNumber; }
    public void setShipmentNumber(String shipmentNumber) { this.shipmentNumber = shipmentNumber; }

    public SalesOrder getSalesOrder() { return salesOrder; }
    public void setSalesOrder(SalesOrder salesOrder) { this.salesOrder = salesOrder; }

    public String getCarrierName() { return carrierName; }
    public void setCarrierName(String carrierName) { this.carrierName = carrierName; }

    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }

    public Location getShippingBayLocation() { return shippingBayLocation; }
    public void setShippingBayLocation(Location shippingBayLocation) { this.shippingBayLocation = shippingBayLocation; }

    public String getDispatchedByUsername() { return dispatchedByUsername; }
    public void setDispatchedByUsername(String dispatchedByUsername) { this.dispatchedByUsername = dispatchedByUsername; }

    public LocalDateTime getDispatchedAt() { return dispatchedAt; }
}

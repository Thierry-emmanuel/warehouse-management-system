package Warehousemanagement.project.order.model;

import Warehousemanagement.project.warehouse.model.Location;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "goods_receipt_notes", indexes = {
    @Index(name = "idx_grn_number", columnList = "receipt_number", unique = true),
    @Index(name = "idx_grn_po", columnList = "purchase_order_id")
})
public class GoodsReceiptNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receipt_number", nullable = false, unique = true, length = 50)
    private String receiptNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dock_location_id")
    private Location dockLocation;

    @Column(name = "carrier_name", length = 100)
    private String carrierName;

    @Column(name = "tracking_number", length = 100)
    private String trackingNumber;

    @Column(name = "received_by_username", nullable = false, length = 50)
    private String receivedByUsername;

    @Column(name = "inspection_notes", length = 500)
    private String inspectionNotes;

    @Column(name = "received_at", nullable = false, updatable = false)
    private LocalDateTime receivedAt = LocalDateTime.now();

    public GoodsReceiptNote() {}

    public GoodsReceiptNote(String receiptNumber, PurchaseOrder purchaseOrder, Location dockLocation,
                            String carrierName, String trackingNumber, String receivedByUsername) {
        this.receiptNumber = receiptNumber;
        this.purchaseOrder = purchaseOrder;
        this.dockLocation = dockLocation;
        this.carrierName = carrierName;
        this.trackingNumber = trackingNumber;
        this.receivedByUsername = receivedByUsername;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getReceiptNumber() { return receiptNumber; }
    public void setReceiptNumber(String receiptNumber) { this.receiptNumber = receiptNumber; }

    public PurchaseOrder getPurchaseOrder() { return purchaseOrder; }
    public void setPurchaseOrder(PurchaseOrder purchaseOrder) { this.purchaseOrder = purchaseOrder; }

    public Location getDockLocation() { return dockLocation; }
    public void setDockLocation(Location dockLocation) { this.dockLocation = dockLocation; }

    public String getCarrierName() { return carrierName; }
    public void setCarrierName(String carrierName) { this.carrierName = carrierName; }

    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }

    public String getReceivedByUsername() { return receivedByUsername; }
    public void setReceivedByUsername(String receivedByUsername) { this.receivedByUsername = receivedByUsername; }

    public String getInspectionNotes() { return inspectionNotes; }
    public void setInspectionNotes(String inspectionNotes) { this.inspectionNotes = inspectionNotes; }

    public LocalDateTime getReceivedAt() { return receivedAt; }
}

package Warehousemanagement.project.product.model;

import Warehousemanagement.project.product.enums.QcStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "batches", indexes = {
    @Index(name = "idx_batch_prod_lot", columnList = "product_id, lot_number", unique = true),
    @Index(name = "idx_batch_expiry", columnList = "expiry_date")
})
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "lot_number", nullable = false, length = 50)
    private String lotNumber;

    @Column(name = "manufacture_date")
    private LocalDate manufactureDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "qc_status", nullable = false, length = 30)
    private QcStatus qcStatus = QcStatus.APPROVED;

    @Column(name = "supplier_batch_ref", length = 100)
    private String supplierBatchRef;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Batch() {}

    public Batch(Product product, String lotNumber, LocalDate manufactureDate, LocalDate expiryDate, QcStatus qcStatus) {
        this.product = product;
        this.lotNumber = lotNumber;
        this.manufactureDate = manufactureDate;
        this.expiryDate = expiryDate;
        this.qcStatus = qcStatus;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public String getLotNumber() { return lotNumber; }
    public void setLotNumber(String lotNumber) { this.lotNumber = lotNumber; }

    public LocalDate getManufactureDate() { return manufactureDate; }
    public void setManufactureDate(LocalDate manufactureDate) { this.manufactureDate = manufactureDate; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public QcStatus getQcStatus() { return qcStatus; }
    public void setQcStatus(QcStatus qcStatus) { this.qcStatus = qcStatus; }

    public String getSupplierBatchRef() { return supplierBatchRef; }
    public void setSupplierBatchRef(String supplierBatchRef) { this.supplierBatchRef = supplierBatchRef; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}

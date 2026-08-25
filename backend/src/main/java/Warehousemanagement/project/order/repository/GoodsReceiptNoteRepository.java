package Warehousemanagement.project.order.repository;

import Warehousemanagement.project.order.model.GoodsReceiptNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoodsReceiptNoteRepository extends JpaRepository<GoodsReceiptNote, Long> {

    Optional<GoodsReceiptNote> findByReceiptNumber(String receiptNumber);

    Page<GoodsReceiptNote> findByPurchaseOrderWarehouseId(Long warehouseId, Pageable pageable);
}

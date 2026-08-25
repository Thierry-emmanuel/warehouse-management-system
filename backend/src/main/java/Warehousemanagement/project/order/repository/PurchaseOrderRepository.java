package Warehousemanagement.project.order.repository;

import Warehousemanagement.project.order.enums.PurchaseOrderStatus;
import Warehousemanagement.project.order.model.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    Optional<PurchaseOrder> findByOrderNumber(String orderNumber);

    boolean existsByOrderNumber(String orderNumber);

    Page<PurchaseOrder> findByWarehouseId(Long warehouseId, Pageable pageable);

    Page<PurchaseOrder> findByWarehouseIdAndStatus(Long warehouseId, PurchaseOrderStatus status, Pageable pageable);

    long countByWarehouseIdAndStatus(Long warehouseId, PurchaseOrderStatus status);
}

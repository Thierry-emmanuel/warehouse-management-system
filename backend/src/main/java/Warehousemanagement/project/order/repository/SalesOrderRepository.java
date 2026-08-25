package Warehousemanagement.project.order.repository;

import Warehousemanagement.project.order.enums.SalesOrderStatus;
import Warehousemanagement.project.order.model.SalesOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {

    Optional<SalesOrder> findByOrderNumber(String orderNumber);

    boolean existsByOrderNumber(String orderNumber);

    Page<SalesOrder> findByWarehouseId(Long warehouseId, Pageable pageable);

    Page<SalesOrder> findByWarehouseIdAndStatus(Long warehouseId, SalesOrderStatus status, Pageable pageable);

    long countByWarehouseIdAndStatus(Long warehouseId, SalesOrderStatus status);
}

package Warehousemanagement.project.inventory.repository;

import Warehousemanagement.project.inventory.enums.MovementType;
import Warehousemanagement.project.inventory.model.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    Page<StockMovement> findByWarehouseId(Long warehouseId, Pageable pageable);

    Page<StockMovement> findByProductId(Long productId, Pageable pageable);

    List<StockMovement> findByWarehouseIdAndTimestampBetween(Long warehouseId, LocalDateTime start, LocalDateTime end);

    long countByWarehouseIdAndMovementType(Long warehouseId, MovementType movementType);
}

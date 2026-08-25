package Warehousemanagement.project.inventory.repository;

import Warehousemanagement.project.inventory.model.InventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    Optional<InventoryItem> findByProductIdAndLocationIdAndBatchId(Long productId, Long locationId, Long batchId);

    Optional<InventoryItem> findByProductIdAndLocationIdAndBatchIsNull(Long productId, Long locationId);

    List<InventoryItem> findByProductId(Long productId);

    Page<InventoryItem> findByLocationWarehouseId(Long warehouseId, Pageable pageable);

    @Query("SELECT SUM(i.quantityOnHand) FROM InventoryItem i WHERE i.product.id = :productId")
    Long sumQuantityOnHandByProductId(@Param("productId") Long productId);

    @Query("SELECT SUM(i.quantityOnHand * i.product.purchasePrice) FROM InventoryItem i WHERE i.location.warehouse.id = :warehouseId")
    Double calculateTotalInventoryValuation(@Param("warehouseId") Long warehouseId);
}

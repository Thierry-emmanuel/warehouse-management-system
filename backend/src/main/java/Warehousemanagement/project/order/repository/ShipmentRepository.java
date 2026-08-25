package Warehousemanagement.project.order.repository;

import Warehousemanagement.project.order.model.Shipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    Optional<Shipment> findByShipmentNumber(String shipmentNumber);

    Page<Shipment> findBySalesOrderWarehouseId(Long warehouseId, Pageable pageable);
}

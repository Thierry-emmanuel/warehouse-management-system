package Warehousemanagement.project.warehouse.repository;

import Warehousemanagement.project.warehouse.enums.ZoneType;
import Warehousemanagement.project.warehouse.model.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {

    List<Zone> findByWarehouseId(Long warehouseId);

    Optional<Zone> findByWarehouseIdAndCode(Long warehouseId, String code);

    Page<Zone> findByWarehouseIdAndZoneType(Long warehouseId, ZoneType zoneType, Pageable pageable);
}

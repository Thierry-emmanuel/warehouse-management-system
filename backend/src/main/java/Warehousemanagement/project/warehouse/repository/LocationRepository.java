package Warehousemanagement.project.warehouse.repository;

import Warehousemanagement.project.warehouse.enums.LocationStatus;
import Warehousemanagement.project.warehouse.enums.LocationType;
import Warehousemanagement.project.warehouse.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByCode(String code);

    boolean existsByCode(String code);

    Page<Location> findByWarehouseId(Long warehouseId, Pageable pageable);

    List<Location> findByWarehouseIdAndLocationType(Long warehouseId, LocationType locationType);

    List<Location> findByWarehouseIdAndLocationTypeAndStatus(Long warehouseId, LocationType locationType, LocationStatus status);

    long countByWarehouseId(Long warehouseId);

    long countByWarehouseIdAndStatus(Long warehouseId, LocationStatus status);
}

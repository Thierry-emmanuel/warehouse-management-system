package Warehousemanagement.project.warehouse.service.impl;

import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.common.exceptions.ResourceNotFoundException;
import Warehousemanagement.project.warehouse.dto.response.LocationResponse;
import Warehousemanagement.project.warehouse.model.Location;
import Warehousemanagement.project.warehouse.repository.LocationRepository;
import Warehousemanagement.project.warehouse.service.LocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<LocationResponse> getLocations(Long warehouseId, String query, Pageable pageable) {
        Page<Location> page = (warehouseId != null)
            ? locationRepository.findByWarehouseId(warehouseId, pageable)
            : locationRepository.findAll(pageable);

        List<LocationResponse> dtos = page.getContent().stream().map(this::toResponse).toList();
        return new PagedResponse<>(dtos, page.getNumber(), page.getSize(), page.getTotalElements(),
                page.getTotalPages(), page.isFirst(), page.isLast(), page.hasNext(), page.hasPrevious());
    }

    @Override
    @Transactional(readOnly = true)
    public LocationResponse getLocationByCode(String code) {
        Location loc = locationRepository.findByCode(code)
            .orElseThrow(() -> new ResourceNotFoundException("Location", "code", code));
        return toResponse(loc);
    }

    private LocationResponse toResponse(Location l) {
        return new LocationResponse(
            l.getId(),
            l.getWarehouse() != null ? l.getWarehouse().getId() : null,
            l.getWarehouse() != null ? l.getWarehouse().getCode() : null,
            l.getCode(),
            l.getBinNumber(),
            l.getLocationType(),
            l.getStatus(),
            l.getMaxWeightKg(),
            l.getMaxVolumeCbm()
        );
    }
}

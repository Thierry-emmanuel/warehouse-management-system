package Warehousemanagement.project.warehouse.service;

import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.warehouse.dto.response.LocationResponse;
import org.springframework.data.domain.Pageable;

public interface LocationService {
    PagedResponse<LocationResponse> getLocations(Long warehouseId, String query, Pageable pageable);
    LocationResponse getLocationByCode(String code);
}

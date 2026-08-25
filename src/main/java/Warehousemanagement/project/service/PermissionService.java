package Warehousemanagement.project.service;

import Warehousemanagement.project.dto.request.CreatePermissionRequest;
import Warehousemanagement.project.dto.request.UpdatePermissionRequest;
import Warehousemanagement.project.dto.response.PagedResponse;
import Warehousemanagement.project.dto.response.PermissionDetailResponse;
import Warehousemanagement.project.dto.response.PermissionSummaryResponse;
import Warehousemanagement.project.enums.PermissionCategory;
import org.springframework.data.domain.Pageable;

public interface PermissionService {

    PermissionDetailResponse createPermission(CreatePermissionRequest request);

    PermissionDetailResponse updatePermission(Long id, UpdatePermissionRequest request);

    PermissionDetailResponse getPermissionById(Long id);

    PagedResponse<PermissionSummaryResponse> getAllPermissions(String query, PermissionCategory category, Pageable pageable);

    void deletePermission(Long id);
}

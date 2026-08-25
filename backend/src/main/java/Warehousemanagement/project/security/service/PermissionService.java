package Warehousemanagement.project.security.service;

import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.security.dto.request.CreatePermissionRequest;
import Warehousemanagement.project.security.dto.request.UpdatePermissionRequest;
import Warehousemanagement.project.security.dto.response.PermissionDetailResponse;
import Warehousemanagement.project.security.dto.response.PermissionSummaryResponse;
import Warehousemanagement.project.security.enums.PermissionCategory;
import org.springframework.data.domain.Pageable;

public interface PermissionService {

    PermissionDetailResponse createPermission(CreatePermissionRequest request);

    PermissionDetailResponse updatePermission(Long id, UpdatePermissionRequest request);

    PermissionDetailResponse getPermissionById(Long id);

    PagedResponse<PermissionSummaryResponse> getAllPermissions(String query, PermissionCategory category, Pageable pageable);

    void deletePermission(Long id);
}

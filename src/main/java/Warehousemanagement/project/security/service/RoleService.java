package Warehousemanagement.project.security.service;

import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.security.dto.request.CreateRoleRequest;
import Warehousemanagement.project.security.dto.request.UpdateRolePermissionsRequest;
import Warehousemanagement.project.security.dto.request.UpdateRoleRequest;
import Warehousemanagement.project.security.dto.response.RoleDetailResponse;
import Warehousemanagement.project.security.dto.response.RoleSummaryResponse;
import org.springframework.data.domain.Pageable;

public interface RoleService {

    RoleDetailResponse createRole(CreateRoleRequest request);

    RoleDetailResponse updateRole(Long id, UpdateRoleRequest request);

    RoleDetailResponse updateRolePermissions(Long id, UpdateRolePermissionsRequest request);

    RoleDetailResponse getRoleById(Long id);

    PagedResponse<RoleSummaryResponse> getAllRoles(String query, Pageable pageable);

    void deleteRole(Long id);
}

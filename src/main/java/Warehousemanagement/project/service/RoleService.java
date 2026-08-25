package Warehousemanagement.project.service;

import Warehousemanagement.project.dto.request.CreateRoleRequest;
import Warehousemanagement.project.dto.request.UpdateRolePermissionsRequest;
import Warehousemanagement.project.dto.request.UpdateRoleRequest;
import Warehousemanagement.project.dto.response.PagedResponse;
import Warehousemanagement.project.dto.response.RoleDetailResponse;
import Warehousemanagement.project.dto.response.RoleSummaryResponse;
import org.springframework.data.domain.Pageable;

public interface RoleService {

    RoleDetailResponse createRole(CreateRoleRequest request);

    RoleDetailResponse updateRole(Long id, UpdateRoleRequest request);

    RoleDetailResponse updateRolePermissions(Long id, UpdateRolePermissionsRequest request);

    RoleDetailResponse getRoleById(Long id);

    PagedResponse<RoleSummaryResponse> getAllRoles(String query, Pageable pageable);

    void deleteRole(Long id);
}

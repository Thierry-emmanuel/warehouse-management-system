package Warehousemanagement.project.security.service;

import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.security.dto.request.CreateUserRequest;
import Warehousemanagement.project.security.dto.request.UpdateUserRequest;
import Warehousemanagement.project.security.dto.response.UserDetailResponse;
import Warehousemanagement.project.security.dto.response.UserSummaryResponse;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDetailResponse createUser(CreateUserRequest request);

    UserDetailResponse updateUser(Long id, UpdateUserRequest request, Long callerWarehouseId);

    UserDetailResponse getUserById(Long id, Long callerWarehouseId);

    PagedResponse<UserSummaryResponse> getAllUsersInWarehouse(Long warehouseId, String query, Pageable pageable);

    void setUserActiveStatus(Long id, boolean isActive, Long callerWarehouseId);
}

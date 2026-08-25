package Warehousemanagement.project.service;

import Warehousemanagement.project.dto.request.CreateUserRequest;
import Warehousemanagement.project.dto.request.UpdateUserRequest;
import Warehousemanagement.project.dto.response.PagedResponse;
import Warehousemanagement.project.dto.response.UserDetailResponse;
import Warehousemanagement.project.dto.response.UserSummaryResponse;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDetailResponse createUser(CreateUserRequest request);

    UserDetailResponse updateUser(Long id, UpdateUserRequest request, Long callerWarehouseId);

    UserDetailResponse getUserById(Long id, Long callerWarehouseId);

    PagedResponse<UserSummaryResponse> getAllUsersInWarehouse(Long warehouseId, String query, Pageable pageable);

    void setUserActiveStatus(Long id, boolean isActive, Long callerWarehouseId);
}

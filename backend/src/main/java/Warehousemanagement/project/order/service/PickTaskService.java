package Warehousemanagement.project.order.service;

import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.order.dto.response.PickTaskResponse;
import org.springframework.data.domain.Pageable;

public interface PickTaskService {
    PagedResponse<PickTaskResponse> getPickTasks(Long warehouseId, String operatorUsername, Pageable pageable);
    PickTaskResponse confirmPick(Long taskId, Integer quantityPicked, String operatorUsername);
}

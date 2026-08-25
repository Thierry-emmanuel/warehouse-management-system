package Warehousemanagement.project.order.service.impl;

import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.common.exceptions.ResourceNotFoundException;
import Warehousemanagement.project.order.dto.response.PickTaskResponse;
import Warehousemanagement.project.order.enums.PickTaskStatus;
import Warehousemanagement.project.order.model.PickTask;
import Warehousemanagement.project.order.repository.PickTaskRepository;
import Warehousemanagement.project.order.service.PickTaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PickTaskServiceImpl implements PickTaskService {

    private final PickTaskRepository pickTaskRepository;

    public PickTaskServiceImpl(PickTaskRepository pickTaskRepository) {
        this.pickTaskRepository = pickTaskRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<PickTaskResponse> getPickTasks(Long warehouseId, String operatorUsername, Pageable pageable) {
        Page<PickTask> page = (operatorUsername != null && !operatorUsername.isBlank())
            ? pickTaskRepository.findByAssignedOperatorUsername(operatorUsername, pageable)
            : pickTaskRepository.findAll(pageable);

        List<PickTaskResponse> dtos = page.getContent().stream().map(this::toResponse).toList();
        return new PagedResponse<>(dtos, page.getNumber(), page.getSize(), page.getTotalElements(),
                page.getTotalPages(), page.isFirst(), page.isLast(), page.hasNext(), page.hasPrevious());
    }

    @Override
    @Transactional
    public PickTaskResponse confirmPick(Long taskId, Integer quantityPicked, String operatorUsername) {
        PickTask task = pickTaskRepository.findById(taskId)
            .orElseThrow(() -> new ResourceNotFoundException("PickTask", "id", taskId));

        task.setPickedQuantity(task.getPickedQuantity() + quantityPicked);
        if (task.getPickedQuantity() >= task.getTargetQuantity()) {
            task.setStatus(PickTaskStatus.COMPLETED);
            task.setCompletedAt(LocalDateTime.now());
        } else {
            task.setStatus(PickTaskStatus.IN_PROGRESS);
        }

        PickTask saved = pickTaskRepository.save(task);
        return toResponse(saved);
    }

    private PickTaskResponse toResponse(PickTask t) {
        return new PickTaskResponse(
            t.getId(),
            t.getTaskCode(),
            t.getSalesOrder() != null ? t.getSalesOrder().getOrderNumber() : null,
            t.getProduct() != null ? t.getProduct().getSku() : null,
            t.getProduct() != null ? t.getProduct().getName() : null,
            t.getProduct() != null ? t.getProduct().getBarcode() : null,
            t.getSourceLocation() != null ? t.getSourceLocation().getCode() : null,
            t.getTargetQuantity(),
            t.getPickedQuantity(),
            t.getStatus(),
            t.getAssignedOperatorUsername(),
            t.getSequenceOrder()
        );
    }
}

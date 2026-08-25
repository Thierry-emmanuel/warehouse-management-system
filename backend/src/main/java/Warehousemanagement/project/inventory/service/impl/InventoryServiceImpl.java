package Warehousemanagement.project.inventory.service.impl;

import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.inventory.dto.response.InventoryItemResponse;
import Warehousemanagement.project.inventory.dto.response.StockMovementResponse;
import Warehousemanagement.project.inventory.model.InventoryItem;
import Warehousemanagement.project.inventory.model.StockMovement;
import Warehousemanagement.project.inventory.repository.InventoryItemRepository;
import Warehousemanagement.project.inventory.repository.StockMovementRepository;
import Warehousemanagement.project.inventory.service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryItemRepository inventoryItemRepository;
    private final StockMovementRepository stockMovementRepository;

    public InventoryServiceImpl(InventoryItemRepository inventoryItemRepository,
                                StockMovementRepository stockMovementRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<InventoryItemResponse> getInventoryBalances(Long warehouseId, String query, Pageable pageable) {
        Page<InventoryItem> page = (warehouseId != null)
            ? inventoryItemRepository.findByLocationWarehouseId(warehouseId, pageable)
            : inventoryItemRepository.findAll(pageable);

        List<InventoryItemResponse> dtos = page.getContent().stream().map(this::toItemResponse).toList();
        return new PagedResponse<>(dtos, page.getNumber(), page.getSize(), page.getTotalElements(),
                page.getTotalPages(), page.isFirst(), page.isLast(), page.hasNext(), page.hasPrevious());
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<StockMovementResponse> getStockMovements(Long warehouseId, Pageable pageable) {
        Page<StockMovement> page = (warehouseId != null)
            ? stockMovementRepository.findByWarehouseIdOrderByTimestampDesc(warehouseId, pageable)
            : stockMovementRepository.findAll(pageable);

        List<StockMovementResponse> dtos = page.getContent().stream().map(this::toMovementResponse).toList();
        return new PagedResponse<>(dtos, page.getNumber(), page.getSize(), page.getTotalElements(),
                page.getTotalPages(), page.isFirst(), page.isLast(), page.hasNext(), page.hasPrevious());
    }

    private InventoryItemResponse toItemResponse(InventoryItem item) {
        return new InventoryItemResponse(
            item.getId(),
            item.getProduct().getId(),
            item.getProduct().getSku(),
            item.getProduct().getName(),
            item.getProduct().getBarcode(),
            item.getProduct().getUnitOfMeasure() != null ? item.getProduct().getUnitOfMeasure().name() : null,
            item.getLocation().getId(),
            item.getLocation().getCode(),
            item.getBatch() != null ? item.getBatch().getBatchNumber() : null,
            item.getBatch() != null && item.getBatch().getExpirationDate() != null ? item.getBatch().getExpirationDate().toString() : null,
            item.getQuantityOnHand(),
            item.getQuantityAllocated(),
            item.getQuantityAvailable(),
            item.getVersion()
        );
    }

    private StockMovementResponse toMovementResponse(StockMovement sm) {
        return new StockMovementResponse(
            sm.getId(),
            sm.getWarehouseId(),
            sm.getProduct().getSku(),
            sm.getProduct().getName(),
            sm.getBatch() != null ? sm.getBatch().getBatchNumber() : null,
            sm.getSourceLocation() != null ? sm.getSourceLocation().getCode() : null,
            sm.getDestinationLocation() != null ? sm.getDestinationLocation().getCode() : null,
            sm.getQuantity(),
            sm.getMovementType().name(),
            sm.getReferenceType(),
            sm.getReferenceId(),
            sm.getOperatorUsername(),
            sm.getTimestamp()
        );
    }
}

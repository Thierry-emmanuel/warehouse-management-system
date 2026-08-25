package Warehousemanagement.project.inventory.service;

import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.inventory.dto.response.InventoryItemResponse;
import Warehousemanagement.project.inventory.dto.response.StockMovementResponse;
import org.springframework.data.domain.Pageable;

public interface InventoryService {
    PagedResponse<InventoryItemResponse> getInventoryBalances(Long warehouseId, String query, Pageable pageable);
    PagedResponse<StockMovementResponse> getStockMovements(Long warehouseId, Pageable pageable);
}

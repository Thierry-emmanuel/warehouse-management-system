import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { InventoryService, InventoryItemDto, StockMovementDto } from '../../core/services/inventory.service';
import { MovementLedgerComponent } from './components/movement-ledger/movement-ledger.component';
import { AdminRackVisualizerComponent } from '../dashboard/admin/components/admin-rack-visualizer/admin-rack-visualizer.component';

@Component({
  selector: 'app-inventory-management',
  standalone: true,
  imports: [CommonModule, FormsModule, MovementLedgerComponent, AdminRackVisualizerComponent],
  templateUrl: './inventory-management.component.html',
  styleUrls: ['./inventory-management.component.css']
})
export class InventoryManagementComponent implements OnInit {
  private inventoryService = inject(InventoryService);

  inventoryItems = signal<InventoryItemDto[]>([]);
  movements = signal<StockMovementDto[]>([]);
  totalBalances = signal<number>(0);
  isLoading = signal<boolean>(true);
  searchQuery = '';

  ngOnInit(): void {
    this.loadInventoryData();
  }

  loadInventoryData(): void {
    this.isLoading.set(true);
    this.inventoryService.getInventoryBalances(0, 50).subscribe({
      next: (res) => {
        this.isLoading.set(false);
        if (res.success && res.data) {
          this.inventoryItems.set(res.data.content);
          this.totalBalances.set(res.data.totalElements);
        }
      },
      error: () => {
        this.isLoading.set(false);
      }
    });

    this.inventoryService.getStockMovements(0, 20).subscribe({
      next: (res) => {
        if (res.success && res.data) {
          this.movements.set(res.data.content);
        }
      }
    });
  }

  get totalOnHand(): number {
    return this.inventoryItems().reduce((acc, item) => acc + item.quantityOnHand, 0);
  }

  get totalAllocated(): number {
    return this.inventoryItems().reduce((acc, item) => acc + item.quantityAllocated, 0);
  }

  get totalAvailable(): number {
    return this.inventoryItems().reduce((acc, item) => acc + item.quantityAvailable, 0);
  }
}

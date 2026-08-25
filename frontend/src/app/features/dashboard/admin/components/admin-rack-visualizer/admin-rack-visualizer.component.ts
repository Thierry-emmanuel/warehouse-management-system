import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InventoryService, InventoryItemDto } from '../../../../../core/services/inventory.service';

export interface BinCell {
  binCode: string;
  level: number;
  col: number;
  occupancyPercent: number;
  sku: string;
  batchNo: string;
  availableQty: number;
  allocatedQty: number;
  status: 'GREEN' | 'YELLOW' | 'RED';
}

@Component({
  selector: 'app-admin-rack-visualizer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-rack-visualizer.component.html',
  styleUrls: ['./admin-rack-visualizer.component.css']
})
export class AdminRackVisualizerComponent implements OnInit {
  private inventoryService = inject(InventoryService);

  selectedBin = signal<BinCell | null>(null);
  bins = signal<BinCell[]>([]);
  isLoading = signal<boolean>(true);

  racks = [
    { name: 'Aisle A — Rack 01 (Mechanical)', prefix: 'A01' },
    { name: 'Aisle B — Rack 02 (Electronics)', prefix: 'B02' }
  ];

  ngOnInit(): void {
    this.loadLiveInventoryCells();
  }

  loadLiveInventoryCells(): void {
    this.isLoading.set(true);
    this.inventoryService.getInventoryBalances(0, 20).subscribe({
      next: (res) => {
        this.isLoading.set(false);
        if (res.success && res.data && res.data.content) {
          const cells = this.mapInventoryToCells(res.data.content);
          this.bins.set(cells);
          if (cells.length > 0) {
            this.selectedBin.set(cells[0]);
          }
        }
      },
      error: () => {
        this.isLoading.set(false);
      }
    });
  }

  private mapInventoryToCells(items: InventoryItemDto[]): BinCell[] {
    return items.map((item, idx) => {
      const level = Math.floor(idx / 4) + 1;
      const col = (idx % 4) + 1;
      const total = item.quantityOnHand + item.quantityAllocated;
      const occupancy = total > 0 ? Math.min(100, Math.round((item.quantityAllocated / total) * 100) + 30) : 10;
      let status: 'GREEN' | 'YELLOW' | 'RED' = 'GREEN';
      if (occupancy > 80) status = 'RED';
      else if (occupancy > 50) status = 'YELLOW';

      return {
        binCode: item.locationCode || `WH1-Z01-A01-S${level}-B0${col}`,
        level,
        col,
        occupancyPercent: occupancy,
        sku: item.productSku,
        batchNo: item.batchNumber || 'N/A',
        availableQty: item.quantityAvailable,
        allocatedQty: item.quantityAllocated,
        status
      };
    });
  }

  onSelectBin(bin: BinCell): void {
    this.selectedBin.set(bin);
  }
}

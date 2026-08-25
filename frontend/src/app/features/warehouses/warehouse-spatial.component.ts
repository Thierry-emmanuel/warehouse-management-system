import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InventoryService, InventoryItemDto } from '../../core/services/inventory.service';
import { AdminRackVisualizerComponent } from '../dashboard/admin/components/admin-rack-visualizer/admin-rack-visualizer.component';

export interface ZoneOverview {
  name: string;
  code: string;
  type: string;
  tempRange: string;
  aisleCount: number;
  rackCount: number;
  occupancyPercent: number;
}

@Component({
  selector: 'app-warehouse-spatial',
  standalone: true,
  imports: [CommonModule, AdminRackVisualizerComponent],
  templateUrl: './warehouse-spatial.component.html',
  styleUrls: ['./warehouse-spatial.component.css']
})
export class WarehouseSpatialComponent implements OnInit {
  private inventoryService = inject(InventoryService);

  activeZone = signal<string>('Z01');
  items = signal<InventoryItemDto[]>([]);
  isLoading = signal<boolean>(true);

  zones: ZoneOverview[] = [
    { name: 'Zone A — Ambient Fast-Pick', code: 'Z01', type: 'AMBIENT', tempRange: '18°C – 22°C', aisleCount: 4, rackCount: 16, occupancyPercent: 78 },
    { name: 'Zone B — High-Bay Cold Storage', code: 'Z02', type: 'COLD_STORAGE', tempRange: '2°C – 6°C', aisleCount: 2, rackCount: 8, occupancyPercent: 88 },
    { name: 'Zone C — Bulk Pallet Reserve', code: 'Z03', type: 'BULK_PALLET', tempRange: 'Ambient', aisleCount: 6, rackCount: 24, occupancyPercent: 62 },
    { name: 'Zone D — Staging & Cross-Dock', code: 'Z04', type: 'STAGING', tempRange: 'Ambient', aisleCount: 2, rackCount: 4, occupancyPercent: 45 }
  ];

  ngOnInit(): void {
    this.loadZoneData();
  }

  loadZoneData(): void {
    this.isLoading.set(true);
    this.inventoryService.getInventoryBalances(0, 50).subscribe({
      next: (res) => {
        this.isLoading.set(false);
        if (res.success && res.data) {
          this.items.set(res.data.content);
        }
      },
      error: () => {
        this.isLoading.set(false);
      }
    });
  }

  selectZone(code: string): void {
    this.activeZone.set(code);
  }
}

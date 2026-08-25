import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

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
export class AdminRackVisualizerComponent {
  selectedBin = signal<BinCell | null>(null);

  racks = [
    { name: 'Aisle A — Rack 01 (Mechanical)', prefix: 'A01' },
    { name: 'Aisle B — Rack 02 (Electronics)', prefix: 'B02' }
  ];

  bins: BinCell[] = [
    { binCode: 'WH1-Z01-A01-S1-B01', level: 1, col: 1, occupancyPercent: 35, sku: 'MECH-GEAR-08', batchNo: 'LOT-9921', availableQty: 180, allocatedQty: 20, status: 'GREEN' },
    { binCode: 'WH1-Z01-A01-S1-B02', level: 1, col: 2, occupancyPercent: 68, sku: 'HYDR-VALVE-02', batchNo: 'LOT-8430', availableQty: 85, allocatedQty: 15, status: 'YELLOW' },
    { binCode: 'WH1-Z01-A01-S1-B03', level: 1, col: 3, occupancyPercent: 92, sku: 'ELEC-AUDIO-01', batchNo: 'LOT-1102', availableQty: 4, allocatedQty: 46, status: 'RED' },
    { binCode: 'WH1-Z01-A01-S1-B04', level: 1, col: 4, occupancyPercent: 45, sku: 'SENS-IND-44', batchNo: 'LOT-4491', availableQty: 120, allocatedQty: 10, status: 'GREEN' },

    { binCode: 'WH1-Z01-A01-S2-B01', level: 2, col: 1, occupancyPercent: 78, sku: 'COLD-SENS-12', batchNo: 'LOT-7821', availableQty: 60, allocatedQty: 30, status: 'YELLOW' },
    { binCode: 'WH1-Z01-A01-S2-B02', level: 2, col: 2, occupancyPercent: 88, sku: 'ELEC-CTRL-09', batchNo: 'LOT-3211', availableQty: 12, allocatedQty: 48, status: 'RED' },
    { binCode: 'WH1-Z01-A01-S2-B03', level: 2, col: 3, occupancyPercent: 25, sku: 'FAST-BOLT-04', batchNo: 'LOT-6540', availableQty: 300, allocatedQty: 20, status: 'GREEN' },
    { binCode: 'WH1-Z01-A01-S2-B04', level: 2, col: 4, occupancyPercent: 60, sku: 'PUMP-ROTARY-11', batchNo: 'LOT-8812', availableQty: 45, allocatedQty: 15, status: 'YELLOW' },

    { binCode: 'WH1-Z01-A01-S3-B01', level: 3, col: 1, occupancyPercent: 95, sku: 'IND-MOTOR-40', batchNo: 'LOT-0914', availableQty: 2, allocatedQty: 38, status: 'RED' },
    { binCode: 'WH1-Z01-A01-S3-B02', level: 3, col: 2, occupancyPercent: 40, sku: 'CBL-FIBER-99', batchNo: 'LOT-5510', availableQty: 150, allocatedQty: 10, status: 'GREEN' },
    { binCode: 'WH1-Z01-A01-S3-B03', level: 3, col: 3, occupancyPercent: 72, sku: 'RELAY-SOLID-03', batchNo: 'LOT-7729', availableQty: 70, allocatedQty: 20, status: 'YELLOW' },
    { binCode: 'WH1-Z01-A01-S3-B04', level: 3, col: 4, occupancyPercent: 30, sku: 'SW-LIMIT-05', batchNo: 'LOT-2311', availableQty: 210, allocatedQty: 15, status: 'GREEN' }
  ];

  constructor() {
    this.selectedBin.set(this.bins[1]);
  }

  onSelectBin(bin: BinCell): void {
    this.selectedBin.set(bin);
  }
}

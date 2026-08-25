import { Component, EventEmitter, Output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

export interface WarehouseZoneNode {
  id: string;
  name: string;
  category: 'STORAGE' | 'TRUCK_DOCK' | 'PARCELS' | 'FORKLIFT' | 'OPERATORS';
  code: string;
  occupancyPercent: number;
  capacityUnits: string;
  assignedOperator: string;
  activeSkus: string[];
  status: 'OPTIMAL' | 'NEAR_CAPACITY' | 'CRITICAL' | 'AVAILABLE';
  temperature?: string;
  details: string;
}

@Component({
  selector: 'app-admin-spatial-twin',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-spatial-twin.component.html',
  styleUrls: ['./admin-spatial-twin.component.css']
})
export class AdminSpatialTwinComponent {
  @Output() selectZone = new EventEmitter<WarehouseZoneNode>();

  selectedZoneId = signal<string>('STORAGE-RACK');
  activeFilter = signal<'ALL' | 'STORAGE' | 'TRUCK_DOCK' | 'PARCELS'>('ALL');

  zones: Record<string, WarehouseZoneNode> = {
    'STORAGE-RACK': {
      id: 'STORAGE-RACK',
      name: 'Multi-Tier Pallet Storage Racks',
      category: 'STORAGE',
      code: 'WH1-Z01-RACKS-A',
      occupancyPercent: 88,
      capacityUnits: '142 / 160 Pallet Bins',
      assignedOperator: 'Roger White (Op #12)',
      activeSkus: ['ELEC-AUDIO-01', 'MECH-GEAR-08', 'HYDR-VALVE-02'],
      status: 'NEAR_CAPACITY',
      temperature: '21.4°C',
      details: 'High-density vertical pallet storage. Multi-tier shelving with active replenishment queue.'
    },
    'TRUCK-DOCK': {
      id: 'TRUCK-DOCK',
      name: 'Inbound Receiving & Truck Bay',
      category: 'TRUCK_DOCK',
      code: 'WH1-DCK-BAY-01',
      occupancyPercent: 75,
      capacityUnits: 'Freight Trailer #TR-409',
      assignedOperator: 'George Russell (Dock Lead)',
      activeSkus: ['Bulk Industrial Motors (40 units)', 'Raw Fasteners'],
      status: 'OPTIMAL',
      details: 'Semi-trailer cargo unloading dock. Dock leveler engaged, active pallet unloader in progress.'
    },
    'PARCEL-STAGING': {
      id: 'PARCEL-STAGING',
      name: 'Online Order Picking & Parcel Staging',
      category: 'PARCEL',
      code: 'WH1-STG-PARCEL-01',
      occupancyPercent: 82,
      capacityUnits: '340 Parcels / 18 Waves',
      assignedOperator: 'Frank Clark (Wave Lead)',
      activeSkus: ['E-Commerce Wave Batch #W-089', 'Retail Express'],
      status: 'OPTIMAL',
      details: 'Sorting conveyor buffer and order consolidation staging area before carrier load.'
    } as unknown as WarehouseZoneNode,
    'FORKLIFT-01': {
      id: 'FORKLIFT-01',
      name: 'Electric Counterbalance Forklift FL-01',
      category: 'FORKLIFT',
      code: 'EQP-FL-01-HEAVY',
      occupancyPercent: 90,
      capacityUnits: '2,500 kg Payload Limit',
      assignedOperator: 'Jesse Miller (Certified Driver)',
      activeSkus: ['Moving Pallet Lot #LOT-9921 -> Rack A-02'],
      status: 'OPTIMAL',
      details: 'Active in central aisle transport route. Telemetry battery 88%, speed 6.2 km/h.'
    },
    'WORKER-TEAM': {
      id: 'WORKER-TEAM',
      name: 'Floor Specialists & Terminal Fleet',
      category: 'OPERATORS',
      code: 'OPS-FLOOR-TEAM-A',
      occupancyPercent: 95,
      capacityUnits: '12 Active Specialists',
      assignedOperator: 'Candy Davis, Robert Lewis & 10 others',
      activeSkus: ['Active Pick & Putaway Missions (18 Tasks)'],
      status: 'OPTIMAL',
      details: 'Wearable barcode terminals active. Average pick turnaround 42 sec/SKU.'
    }
  };

  onSelect(zoneKey: string): void {
    const zone = this.zones[zoneKey];
    if (zone) {
      this.selectedZoneId.set(zone.id);
      this.selectZone.emit(zone);
    }
  }

  setFilter(filter: 'ALL' | 'STORAGE' | 'TRUCK_DOCK' | 'PARCELS'): void {
    this.activeFilter.set(filter);
  }
}

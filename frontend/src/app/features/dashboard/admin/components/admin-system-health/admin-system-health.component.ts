import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-system-health',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-system-health.component.html',
  styleUrls: ['./admin-system-health.component.css']
})
export class AdminSystemHealthComponent {
  @Input() facilityStatus: Record<string, unknown> | null = null;
  @Input() systemHealth: Record<string, unknown> | null = null;

  gateways = [
    { id: 'GTW-01', location: 'Dock A (Inbound)', status: 'Online', latency: '24ms', devices: 6 },
    { id: 'GTW-02', location: 'Aisle Racks 1-12', status: 'Online', latency: '31ms', devices: 5 },
    { id: 'GTW-03', location: 'Packing Zone C', status: 'Online', latency: '19ms', devices: 4 },
    { id: 'GTW-04', location: 'Dock B (Outbound)', status: 'Online', latency: '28ms', devices: 3 }
  ];
}

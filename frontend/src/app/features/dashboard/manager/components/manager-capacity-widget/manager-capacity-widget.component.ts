import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-manager-capacity-widget',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './manager-capacity-widget.component.html',
  styleUrls: ['./manager-capacity-widget.component.css']
})
export class ManagerCapacityWidgetComponent {
  @Input() capacityUsage = 0.82;

  zones = [
    { name: 'Zone A — Ambient Bulk Pallet Racks', code: 'Z-01-AMB', fill: 78, max: '600 Pallets', status: 'Optimal', barColor: 'bar-blue' },
    { name: 'Zone B — Cold Storage Racks (4°C)', code: 'Z-02-CLD', fill: 88, max: '320 Pallets', status: 'Near Max', barColor: 'bar-amber' },
    { name: 'Zone C — QC Quarantine & Inspection', code: 'Z-03-QAR', fill: 45, max: '150 Pallets', status: 'Optimal', barColor: 'bar-emerald' },
    { name: 'Zone D — Staging & Dispatch Lanes', code: 'Z-04-STG', fill: 62, max: '400 Pallets', status: 'Optimal', barColor: 'bar-purple' }
  ];
}

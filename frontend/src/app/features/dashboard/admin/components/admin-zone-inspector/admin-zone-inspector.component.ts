import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WarehouseZoneNode } from '../admin-spatial-twin/admin-spatial-twin.component';

@Component({
  selector: 'app-admin-zone-inspector',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-zone-inspector.component.html',
  styleUrls: ['./admin-zone-inspector.component.css']
})
export class AdminZoneInspectorComponent {
  @Input() selectedZone: WarehouseZoneNode | null = null;
}

import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManagerDashboardResponse } from '../../../../../core/models/dashboard.models';

@Component({
  selector: 'app-manager-metrics-row',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './manager-metrics-row.component.html',
  styleUrls: ['./manager-metrics-row.component.css']
})
export class ManagerMetricsRowComponent {
  @Input() data: ManagerDashboardResponse | null = null;
}

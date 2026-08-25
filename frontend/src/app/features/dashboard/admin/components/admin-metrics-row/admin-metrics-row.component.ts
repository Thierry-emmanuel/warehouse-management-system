import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminDashboardResponse } from '../../../../../core/models/dashboard.models';

@Component({
  selector: 'app-admin-metrics-row',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-metrics-row.component.html',
  styleUrls: ['./admin-metrics-row.component.css']
})
export class AdminMetricsRowComponent {
  @Input() data: AdminDashboardResponse | null = null;
}

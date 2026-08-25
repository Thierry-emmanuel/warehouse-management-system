import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardService } from '../../../core/services/dashboard.service';
import { ManagerDashboardResponse } from '../../../core/models/dashboard.models';
import { ManagerMetricsRowComponent } from './components/manager-metrics-row/manager-metrics-row.component';
import { ManagerCapacityWidgetComponent } from './components/manager-capacity-widget/manager-capacity-widget.component';
import { ManagerAlertsFeedComponent } from './components/manager-alerts-feed/manager-alerts-feed.component';
import { AdminActivityHeatmapComponent } from '../admin/components/admin-activity-heatmap/admin-activity-heatmap.component';
import { AdminTrendChartComponent } from '../admin/components/admin-trend-chart/admin-trend-chart.component';
import { AdminRackVisualizerComponent } from '../admin/components/admin-rack-visualizer/admin-rack-visualizer.component';

@Component({
  selector: 'app-manager-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    ManagerMetricsRowComponent,
    ManagerCapacityWidgetComponent,
    ManagerAlertsFeedComponent,
    AdminActivityHeatmapComponent,
    AdminTrendChartComponent,
    AdminRackVisualizerComponent
  ],
  templateUrl: './manager-dashboard.component.html',
  styleUrls: ['./manager-dashboard.component.css']
})
export class ManagerDashboardComponent implements OnInit {
  private dashboardService = inject(DashboardService);
  managerData = signal<ManagerDashboardResponse | null>(null);
  isLoading = signal<boolean>(true);
  errorMessage = signal<string | null>(null);

  ngOnInit(): void {
    this.loadManagerData();
  }

  loadManagerData(): void {
    this.isLoading.set(true);
    this.errorMessage.set(null);
    this.dashboardService.getManagerDashboard().subscribe({
      next: (res) => {
        this.isLoading.set(false);
        if (res && res.success && res.data) {
          this.managerData.set(res.data);
        }
      },
      error: () => {
        this.isLoading.set(false);
        this.errorMessage.set('Unable to retrieve manager supply chain telemetry from backend.');
      }
    });
  }
}

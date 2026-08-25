import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardService } from '../../../core/services/dashboard.service';
import { UserService } from '../../../core/services/user.service';
import { AuthService } from '../../../core/services/auth.service';
import { AdminDashboardResponse, ManagerDashboardResponse } from '../../../core/models/dashboard.models';
import { UserSummary } from '../../../core/models/user.models';
import { AdminMetricsRowComponent } from './components/admin-metrics-row/admin-metrics-row.component';
import { AdminActivityHeatmapComponent } from './components/admin-activity-heatmap/admin-activity-heatmap.component';
import { AdminTrendChartComponent } from './components/admin-trend-chart/admin-trend-chart.component';
import { AdminRackVisualizerComponent } from './components/admin-rack-visualizer/admin-rack-visualizer.component';
import { AdminRoleDistributionComponent } from './components/admin-role-distribution/admin-role-distribution.component';
import { AdminUserTableComponent } from './components/admin-user-table/admin-user-table.component';
import { AdminSystemHealthComponent } from './components/admin-system-health/admin-system-health.component';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    AdminMetricsRowComponent,
    AdminActivityHeatmapComponent,
    AdminTrendChartComponent,
    AdminRackVisualizerComponent,
    AdminRoleDistributionComponent,
    AdminUserTableComponent,
    AdminSystemHealthComponent
  ],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  private authService = inject(AuthService);
  private dashboardService = inject(DashboardService);
  private userService = inject(UserService);

  dashboardData = signal<AdminDashboardResponse | null>(null);
  managerData = signal<ManagerDashboardResponse | null>(null);
  users = signal<UserSummary[]>([]);
  totalUsers = signal<number>(0);
  isLoading = signal<boolean>(true);
  errorMessage = signal<string | null>(null);

  ngOnInit(): void {
    this.initLiveDashboard();
  }

  initLiveDashboard(): void {
    this.isLoading.set(true);
    this.errorMessage.set(null);

    this.authService.ensureAuthenticated().subscribe({
      next: () => {
        this.fetchLiveMetrics();
      },
      error: () => {
        this.isLoading.set(false);
        this.errorMessage.set('Authentication failed. Unable to establish secure session with backend API.');
      }
    });
  }

  fetchLiveMetrics(): void {
    this.dashboardService.getAdminDashboard().subscribe({
      next: (res) => {
        this.isLoading.set(false);
        if (res && res.success && res.data) {
          this.dashboardData.set(res.data);
        }
      },
      error: () => {
        this.isLoading.set(false);
        this.errorMessage.set('Failed to load live admin telemetry from backend at http://localhost:8080.');
      }
    });

    this.dashboardService.getManagerDashboard().subscribe({
      next: (res) => {
        if (res && res.success && res.data) {
          this.managerData.set(res.data);
        }
      }
    });

    this.loadUsers();
  }

  loadUsers(query = ''): void {
    this.userService.getUsers(0, 20, query).subscribe({
      next: (res) => {
        if (res && res.success && res.data) {
          this.users.set(res.data.content);
          this.totalUsers.set(res.data.totalElements);
        }
      },
      error: () => {
        this.users.set([]);
        this.totalUsers.set(0);
      }
    });
  }

  onUserSearch(query: string): void {
    this.loadUsers(query);
  }

  onToggleUserStatus(event: { id: number; isActive: boolean }): void {
    this.userService.setUserStatus(event.id, event.isActive).subscribe({
      next: () => {
        this.loadUsers();
      }
    });
  }
}

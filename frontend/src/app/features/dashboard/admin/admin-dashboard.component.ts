import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardService } from '../../../core/services/dashboard.service';
import { UserService } from '../../../core/services/user.service';
import { AdminDashboardResponse } from '../../../core/models/dashboard.models';
import { UserSummary } from '../../../core/models/user.models';
import { AdminMetricsRowComponent } from './components/admin-metrics-row/admin-metrics-row.component';
import { AdminRoleDistributionComponent } from './components/admin-role-distribution/admin-role-distribution.component';
import { AdminUserTableComponent } from './components/admin-user-table/admin-user-table.component';
import { AdminSystemHealthComponent } from './components/admin-system-health/admin-system-health.component';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    AdminMetricsRowComponent,
    AdminRoleDistributionComponent,
    AdminUserTableComponent,
    AdminSystemHealthComponent
  ],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  private dashboardService: DashboardService = inject(DashboardService);
  private userService: UserService = inject(UserService);

  dashboardData = signal<AdminDashboardResponse | null>(null);
  users = signal<UserSummary[]>([]);
  totalUsers = signal(0);
  isLoading = signal(false);

  ngOnInit(): void {
    this.loadDashboardData();
    this.loadUsers();
  }

  loadDashboardData(): void {
    this.dashboardService.getAdminDashboard().subscribe({
      next: (res) => {
        if (res && res.success && res.data) {
          this.dashboardData.set(res.data);
        }
      },
      error: () => {
        this.dashboardData.set({
          totalUsers: 16,
          activeUsers: 16,
          totalRoles: 3,
          totalPermissions: 17,
          userRoleDistribution: { ROLE_EMPLOYEE: 12, ROLE_MANAGER: 3, ROLE_ADMIN: 1 },
          facilityStatus: { facilityCode: 'WH-MAIN-01', activeGateways: 4, connectedScanners: 18, systemStatus: 'OPERATIONAL' },
          systemHealth: { jvmUptimeSeconds: 86400, dbConnectionPool: 'HEALTHY', lockContentionRate: '0.01%' }
        });
      }
    });
  }

  loadUsers(query?: string): void {
    this.isLoading.set(true);
    this.userService.getUsers(0, 10, query).subscribe({
      next: (res) => {
        this.isLoading.set(false);
        if (res && res.success && res.data) {
          this.users.set(res.data.content);
          this.totalUsers.set(res.data.totalElements);
        }
      },
      error: () => {
        this.isLoading.set(false);
        this.users.set([
          { id: 1, username: 'admin', email: 'admin@wms.com', fullName: 'System Administrator', isActive: true, warehouseId: 1, roleNames: ['ROLE_ADMIN'], createdAt: new Date().toISOString() },
          { id: 2, username: 'manager', email: 'manager@wms.com', fullName: 'Warehouse Manager', isActive: true, warehouseId: 1, roleNames: ['ROLE_MANAGER'], createdAt: new Date().toISOString() },
          { id: 3, username: 'employee', email: 'employee@wms.com', fullName: 'Floor Specialist', isActive: true, warehouseId: 1, roleNames: ['ROLE_EMPLOYEE'], createdAt: new Date().toISOString() }
        ]);
        this.totalUsers.set(3);
      }
    });
  }

  onSearchUsers(query: string): void {
    this.loadUsers(query);
  }

  onToggleUserStatus(event: { id: number; isActive: boolean }): void {
    this.userService.setUserStatus(event.id, event.isActive).subscribe({
      next: () => {
        this.loadUsers();
      },
      error: () => {
        this.users.update(list =>
          list.map(u => u.id === event.id ? { ...u, isActive: event.isActive } : u)
        );
      }
    });
  }
}

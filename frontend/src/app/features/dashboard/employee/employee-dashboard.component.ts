import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardService } from '../../../core/services/dashboard.service';
import { AuthService } from '../../../core/services/auth.service';
import { EmployeeDashboardResponse } from '../../../core/models/dashboard.models';
import { EmployeeKpiRowComponent } from './components/employee-kpi-row/employee-kpi-row.component';
import { EmployeeScannerPanelComponent } from './components/employee-scanner-panel/employee-scanner-panel.component';
import { EmployeeTaskQueueComponent } from './components/employee-task-queue/employee-task-queue.component';
import { AdminRackVisualizerComponent } from '../admin/components/admin-rack-visualizer/admin-rack-visualizer.component';

@Component({
  selector: 'app-employee-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    EmployeeKpiRowComponent,
    EmployeeScannerPanelComponent,
    EmployeeTaskQueueComponent,
    AdminRackVisualizerComponent
  ],
  templateUrl: './employee-dashboard.component.html',
  styleUrls: ['./employee-dashboard.component.css']
})
export class EmployeeDashboardComponent implements OnInit {
  private dashboardService = inject(DashboardService);
  authService = inject(AuthService);

  employeeData = signal<EmployeeDashboardResponse | null>(null);
  isLoading = signal<boolean>(true);
  errorMessage = signal<string | null>(null);

  ngOnInit(): void {
    this.loadEmployeeData();
  }

  loadEmployeeData(): void {
    this.isLoading.set(true);
    this.errorMessage.set(null);
    this.dashboardService.getEmployeeDashboard().subscribe({
      next: (res) => {
        this.isLoading.set(false);
        if (res && res.success && res.data) {
          this.employeeData.set(res.data);
        }
      },
      error: () => {
        this.isLoading.set(false);
        this.errorMessage.set('Unable to retrieve employee floor missions from backend.');
      }
    });
  }

  onScanComplete(event: { barcode: string; step: number }): void {
    this.loadEmployeeData();
  }
}

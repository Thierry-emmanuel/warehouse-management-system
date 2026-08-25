import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeDashboardResponse } from '../../../../../core/models/dashboard.models';

@Component({
  selector: 'app-employee-kpi-row',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './employee-kpi-row.component.html',
  styleUrls: ['./employee-kpi-row.component.css']
})
export class EmployeeKpiRowComponent {
  @Input() data: EmployeeDashboardResponse | null = null;
}

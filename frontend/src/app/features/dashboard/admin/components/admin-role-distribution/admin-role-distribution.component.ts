import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

interface RoleProgress {
  name: string;
  roleCode: string;
  count: number;
  percentage: number;
  colorClass: string;
}

@Component({
  selector: 'app-admin-role-distribution',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-role-distribution.component.html',
  styleUrls: ['./admin-role-distribution.component.css']
})
export class AdminRoleDistributionComponent {
  @Input() distribution: Record<string, number> | null = null;

  roles: RoleProgress[] = [
    { name: 'Floor Operations Specialist', roleCode: 'ROLE_EMPLOYEE', count: 12, percentage: 75, colorClass: 'bar-blue' },
    { name: 'Warehouse Operations Manager', roleCode: 'ROLE_MANAGER', count: 3, percentage: 19, colorClass: 'bar-emerald' },
    { name: 'System Administrator', roleCode: 'ROLE_ADMIN', count: 1, percentage: 6, colorClass: 'bar-purple' }
  ];

  permissionCategories = [
    { name: 'Administration', count: 4, badgeClass: 'badge-purple' },
    { name: 'Inventory & Stock', count: 4, badgeClass: 'badge-blue' },
    { name: 'Procurement (PO)', count: 2, badgeClass: 'badge-emerald' },
    { name: 'Floor Operations (Pick/Pack)', count: 6, badgeClass: 'badge-orange' },
    { name: 'Reports & Analytics', count: 1, badgeClass: 'badge-slate' }
  ];
}

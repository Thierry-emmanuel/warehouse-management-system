import { Component, Input, OnChanges } from '@angular/core';
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
export class AdminRoleDistributionComponent implements OnChanges {
  @Input() distribution: Record<string, number> | null = null;
  @Input() totalUsers = 0;

  roles: RoleProgress[] = [];

  permissionCategories = [
    { name: 'Administration', count: 4, badgeClass: 'badge-purple' },
    { name: 'Inventory & Stock', count: 4, badgeClass: 'badge-blue' },
    { name: 'Procurement (PO)', count: 2, badgeClass: 'badge-emerald' },
    { name: 'Floor Operations (Pick/Pack)', count: 6, badgeClass: 'badge-orange' },
    { name: 'Reports & Analytics', count: 1, badgeClass: 'badge-slate' }
  ];

  ngOnChanges(): void {
    if (this.distribution) {
      const colors = ['bar-purple', 'bar-emerald', 'bar-blue', 'bar-orange'];
      const friendlyNames: Record<string, string> = {
        'ROLE_ADMIN': 'System Administrator',
        'ROLE_MANAGER': 'Warehouse Operations Manager',
        'ROLE_EMPLOYEE': 'Floor Operations Specialist',
        'ROLE_SUPERVISOR': 'Shift Supervisor'
      };

      const keys = Object.keys(this.distribution);
      const total = this.totalUsers > 0 ? this.totalUsers : keys.reduce((acc, k) => acc + (this.distribution?.[k] || 0), 0);

      this.roles = keys.map((roleCode, idx) => {
        const count = this.distribution?.[roleCode] || 0;
        const percentage = total > 0 ? Math.round((count / total) * 100) : 0;
        return {
          name: friendlyNames[roleCode] || roleCode,
          roleCode,
          count,
          percentage,
          colorClass: colors[idx % colors.length]
        };
      });
    } else {
      this.roles = [];
    }
  }
}

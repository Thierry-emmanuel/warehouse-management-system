import { Component, computed, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../../core/services/auth.service';

interface NavItem {
  label: string;
  route: string;
  icon: string;
  roles?: string[];
  badge?: string;
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  authService = inject(AuthService);

  private readonly allNavItems: NavItem[] = [
    {
      label: 'Admin Telemetry',
      route: '/dashboard/admin',
      roles: ['ROLE_ADMIN'],
      icon: 'M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6'
    },
    {
      label: 'Manager Supply Chain',
      route: '/dashboard/manager',
      roles: ['ROLE_ADMIN', 'ROLE_MANAGER'],
      icon: 'M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z'
    },
    {
      label: 'My Floor Operations',
      route: '/dashboard/employee',
      roles: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_EMPLOYEE', 'ROLE_SUPERVISOR'],
      icon: 'M12 4v1m6 11h2m-6 0h-2v4m0-11v3m0 0h.01M12 12h4.01M16 20h4M4 12h4m12 0h.01M5 8h2a1 1 0 001-1V5a1 1 0 00-1-1H5a1 1 0 00-1 1v2a1 1 0 001 1zm12 0h2a1 1 0 001-1V5a1 1 0 00-1-1h-2a1 1 0 00-1 1v2a1 1 0 001 1zM5 20h2a1 1 0 001-1v-2a1 1 0 00-1-1H5a1 1 0 00-1 1v2a1 1 0 001 1z'
    },
    {
      label: 'Staff & Operators',
      route: '/users',
      roles: ['ROLE_ADMIN'],
      icon: 'M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z'
    },
    {
      label: 'Roles & Permissions',
      route: '/roles',
      roles: ['ROLE_ADMIN'],
      icon: 'M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z'
    },
    {
      label: 'SKU Categories',
      route: '/categories',
      roles: ['ROLE_ADMIN', 'ROLE_MANAGER'],
      icon: 'M7 7h.01M7 3h5c.512 0 1.024.195 1.414.586l7 7a2 2 0 010 2.828l-7 7a2 2 0 01-2.828 0l-7-7A1.994 1.994 0 013 12V7a4 4 0 014-4z'
    }
  ];

  navItems = computed(() => {
    const user = this.authService.currentUser();
    if (!user) return [];
    return this.allNavItems.filter(item => {
      if (!item.roles || item.roles.length === 0) return true;
      return item.roles.some(role => user.roles.includes(role));
    });
  });

  get userInitials(): string {
    const user = this.authService.currentUser();
    if (!user || !user.fullName) return 'OP';
    return user.fullName.split(' ').map(n => n[0]).join('').substring(0, 2).toUpperCase();
  }

  get friendlyRoleTitle(): string {
    const user = this.authService.currentUser();
    if (!user || !user.roles.length) return 'Operator';
    if (user.roles.includes('ROLE_ADMIN')) return 'System Administrator';
    if (user.roles.includes('ROLE_MANAGER')) return 'Warehouse Manager';
    return 'Floor Operations Specialist';
  }

  logout(): void {
    this.authService.logout();
  }
}

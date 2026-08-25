import { Routes } from '@angular/router';
import { MainLayoutComponent } from './shared/components/layout/main-layout/main-layout.component';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { AdminDashboardComponent } from './features/dashboard/admin/admin-dashboard.component';
import { ManagerDashboardComponent } from './features/dashboard/manager/manager-dashboard.component';
import { EmployeeDashboardComponent } from './features/dashboard/employee/employee-dashboard.component';
import { UserManagementComponent } from './features/users/user-management.component';
import { RoleManagementComponent } from './features/roles/role-management.component';
import { CategoryManagementComponent } from './features/categories/category-management.component';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';
import { dashboardRedirectGuard } from './core/guards/dashboard-redirect.guard';

export const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
    title: 'LogistiQ — Operator Terminal Sign In'
  },
  {
    path: 'register',
    component: RegisterComponent,
    title: 'LogistiQ — Onboard Operator'
  },
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [authGuard],
    children: [
      {
        path: '',
        pathMatch: 'full',
        canActivate: [dashboardRedirectGuard],
        children: []
      },
      {
        path: 'dashboard',
        pathMatch: 'full',
        canActivate: [dashboardRedirectGuard],
        children: []
      },
      {
        path: 'dashboard/admin',
        component: AdminDashboardComponent,
        canActivate: [roleGuard],
        data: { roles: ['ROLE_ADMIN'] },
        title: 'LogistiQ — Admin Dashboard'
      },
      {
        path: 'dashboard/manager',
        component: ManagerDashboardComponent,
        canActivate: [roleGuard],
        data: { roles: ['ROLE_MANAGER', 'ROLE_ADMIN'] },
        title: 'LogistiQ — Manager Dashboard'
      },
      {
        path: 'dashboard/employee',
        component: EmployeeDashboardComponent,
        canActivate: [roleGuard],
        data: { roles: ['ROLE_EMPLOYEE', 'ROLE_SUPERVISOR', 'ROLE_MANAGER', 'ROLE_ADMIN'] },
        title: 'LogistiQ — My Dedicated Floor Dashboard'
      },
      {
        path: 'users',
        component: UserManagementComponent,
        canActivate: [roleGuard],
        data: { roles: ['ROLE_ADMIN'] },
        title: 'LogistiQ — Staff & Operators'
      },
      {
        path: 'roles',
        component: RoleManagementComponent,
        canActivate: [roleGuard],
        data: { roles: ['ROLE_ADMIN'] },
        title: 'LogistiQ — RBAC Roles & Privileges'
      },
      {
        path: 'categories',
        component: CategoryManagementComponent,
        canActivate: [roleGuard],
        data: { roles: ['ROLE_ADMIN', 'ROLE_MANAGER'] },
        title: 'LogistiQ — SKU Category Taxonomy'
      }
    ]
  },
  {
    path: '**',
    redirectTo: 'login'
  }
];

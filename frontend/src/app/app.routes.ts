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
    children: [
      {
        path: '',
        redirectTo: 'dashboard/admin',
        pathMatch: 'full'
      },
      {
        path: 'dashboard/admin',
        component: AdminDashboardComponent,
        title: 'LogistiQ — Admin Dashboard'
      },
      {
        path: 'dashboard/manager',
        component: ManagerDashboardComponent,
        title: 'LogistiQ — Manager Dashboard'
      },
      {
        path: 'dashboard/employee',
        component: EmployeeDashboardComponent,
        title: 'LogistiQ — Floor Scanner Dashboard'
      },
      {
        path: 'users',
        component: UserManagementComponent,
        title: 'LogistiQ — Staff & Operators'
      },
      {
        path: 'roles',
        component: RoleManagementComponent,
        title: 'LogistiQ — RBAC Roles & Privileges'
      },
      {
        path: 'categories',
        component: CategoryManagementComponent,
        title: 'LogistiQ — SKU Category Taxonomy'
      }
    ]
  },
  {
    path: '**',
    redirectTo: 'login'
  }
];

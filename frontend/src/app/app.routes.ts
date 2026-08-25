import { Routes } from '@angular/router';
import { MainLayoutComponent } from './shared/components/layout/main-layout/main-layout.component';
import { AdminDashboardComponent } from './features/dashboard/admin/admin-dashboard.component';

export const routes: Routes = [
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
      }
    ]
  },
  {
    path: '**',
    redirectTo: 'dashboard/admin'
  }
];

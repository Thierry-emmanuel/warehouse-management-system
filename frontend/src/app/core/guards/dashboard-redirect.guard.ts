import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const dashboardRedirectGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (!authService.isAuthenticated()) {
    router.navigate(['/login']);
    return false;
  }

  const user = authService.currentUser();
  if (user?.roles.includes('ROLE_ADMIN')) {
    router.navigate(['/dashboard/admin']);
  } else if (user?.roles.includes('ROLE_MANAGER')) {
    router.navigate(['/dashboard/manager']);
  } else {
    router.navigate(['/dashboard/employee']);
  }

  return false;
};

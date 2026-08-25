import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const roleGuard: CanActivateFn = (route) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (!authService.isAuthenticated()) {
    router.navigate(['/login']);
    return false;
  }

  const expectedRoles: string[] = route.data?.['roles'] || [];
  if (expectedRoles.length === 0) {
    return true;
  }

  const user = authService.currentUser();
  const hasRole = expectedRoles.some(r => user?.roles.includes(r));

  if (hasRole) {
    return true;
  }

  // Redirect to user's personal dashboard if unauthorized for this specific route
  if (user?.roles.includes('ROLE_ADMIN')) {
    router.navigate(['/dashboard/admin']);
  } else if (user?.roles.includes('ROLE_MANAGER')) {
    router.navigate(['/dashboard/manager']);
  } else {
    router.navigate(['/dashboard/employee']);
  }

  return false;
};

import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  username = '';
  password = '';
  rememberMe = true;
  isLoading = signal<boolean>(false);
  errorMessage = signal<string | null>(null);

  demoAccounts = [
    { role: 'Admin', user: 'admin', pass: 'Admin@12345', desc: 'Central Platform Setup & RBAC' },
    { role: 'Manager', user: 'manager', pass: 'Manager@12345', desc: 'Supply Chain & Dock Receiving' },
    { role: 'Employee', user: 'employee', pass: 'Employee@12345', desc: 'Directed Scanner & Wave Picking' }
  ];

  fillDemo(user: string, pass: string): void {
    this.username = user;
    this.password = pass;
    this.errorMessage.set(null);
  }

  onSubmit(): void {
    if (!this.username || !this.password) {
      this.errorMessage.set('Please enter both username and password.');
      return;
    }

    this.isLoading.set(true);
    this.errorMessage.set(null);

    this.authService.login({ username: this.username.trim(), password: this.password }).subscribe({
      next: (res) => {
        this.isLoading.set(false);
        if (res.success && res.data) {
          const roles = res.data.roles;
          if (roles.includes('ROLE_ADMIN')) {
            this.router.navigate(['/dashboard/admin']);
          } else if (roles.includes('ROLE_MANAGER')) {
            this.router.navigate(['/dashboard/manager']);
          } else {
            this.router.navigate(['/dashboard/employee']);
          }
        }
      },
      error: (err) => {
        this.isLoading.set(false);
        const msg = err.error?.message || 'Authentication failed. Please verify credentials or connection.';
        this.errorMessage.set(msg);
      }
    });
  }
}

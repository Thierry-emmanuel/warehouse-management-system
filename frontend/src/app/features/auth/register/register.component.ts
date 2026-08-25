import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { RegisterRequest } from '../../../core/models/auth.models';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  formData: RegisterRequest = {
    username: '',
    email: '',
    fullName: '',
    password: '',
    phoneNumber: '',
    warehouseId: 1
  };

  isLoading = signal<boolean>(false);
  errorMessage = signal<string | null>(null);

  onSubmit(): void {
    if (!this.formData.username || !this.formData.email || !this.formData.fullName || !this.formData.password) {
      this.errorMessage.set('Please fill in all mandatory registration fields.');
      return;
    }

    this.isLoading.set(true);
    this.errorMessage.set(null);

    this.authService.register(this.formData).subscribe({
      next: (res) => {
        this.isLoading.set(false);
        if (res.success && res.data) {
          this.router.navigate(['/dashboard/employee']);
        }
      },
      error: (err) => {
        this.isLoading.set(false);
        const msg = err.error?.message || 'Registration failed. Please verify user details.';
        this.errorMessage.set(msg);
      }
    });
  }
}

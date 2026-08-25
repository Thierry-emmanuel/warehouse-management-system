import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../core/services/user.service';
import { RoleService } from '../../core/services/role.service';
import { UserSummary } from '../../core/models/user.models';
import { UserModalComponent, UserFormData } from './components/user-modal/user-modal.component';

@Component({
  selector: 'app-user-management',
  standalone: true,
  imports: [CommonModule, FormsModule, UserModalComponent],
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent implements OnInit {
  private userService = inject(UserService);
  private roleService = inject(RoleService);

  users = signal<UserSummary[]>([]);
  totalUsers = signal<number>(0);
  availableRoles = signal<Array<{ id: number; name: string }>>([]);
  isLoading = signal<boolean>(true);
  isModalOpen = signal<boolean>(false);
  searchQuery = '';

  ngOnInit(): void {
    this.loadUsers();
    this.loadRoles();
  }

  loadUsers(): void {
    this.isLoading.set(true);
    this.userService.getUsers(0, 50, this.searchQuery).subscribe({
      next: (res) => {
        this.isLoading.set(false);
        if (res.success && res.data) {
          this.users.set(res.data.content);
          this.totalUsers.set(res.data.totalElements);
        }
      },
      error: () => {
        this.isLoading.set(false);
        this.users.set([]);
      }
    });
  }

  loadRoles(): void {
    this.roleService.getRoles(0, 50).subscribe({
      next: (res) => {
        if (res.success && res.data) {
          this.availableRoles.set(res.data.content.map(r => ({ id: r.id, name: r.name })));
        }
      }
    });
  }

  onSearch(): void {
    this.loadUsers();
  }

  openModal(): void {
    this.isModalOpen.set(true);
  }

  closeModal(): void {
    this.isModalOpen.set(false);
  }

  onSaveUser(data: UserFormData): void {
    this.closeModal();
    this.loadUsers();
  }

  toggleStatus(user: UserSummary): void {
    this.userService.setUserStatus(user.id, !user.isActive).subscribe({
      next: () => this.loadUsers()
    });
  }
}

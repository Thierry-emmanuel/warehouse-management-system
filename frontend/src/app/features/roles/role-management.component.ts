import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RoleService, CreateRoleRequest } from '../../core/services/role.service';
import { PermissionService } from '../../core/services/permission.service';
import { RoleSummary, PermissionSummary } from '../../core/models/user.models';
import { RoleModalComponent } from './components/role-modal/role-modal.component';

@Component({
  selector: 'app-role-management',
  standalone: true,
  imports: [CommonModule, FormsModule, RoleModalComponent],
  templateUrl: './role-management.component.html',
  styleUrls: ['./role-management.component.css']
})
export class RoleManagementComponent implements OnInit {
  private roleService = inject(RoleService);
  private permissionService = inject(PermissionService);

  roles = signal<RoleSummary[]>([]);
  permissions = signal<PermissionSummary[]>([]);
  isLoading = signal<boolean>(true);
  isModalOpen = signal<boolean>(false);

  ngOnInit(): void {
    this.loadRoles();
    this.loadPermissions();
  }

  loadRoles(): void {
    this.isLoading.set(true);
    this.roleService.getRoles(0, 50).subscribe({
      next: (res) => {
        this.isLoading.set(false);
        if (res.success && res.data) {
          this.roles.set(res.data.content);
        }
      },
      error: () => {
        this.isLoading.set(false);
        this.roles.set([]);
      }
    });
  }

  loadPermissions(): void {
    this.permissionService.getPermissions(0, 100).subscribe({
      next: (res) => {
        if (res.success && res.data) {
          this.permissions.set(res.data.content);
        }
      }
    });
  }

  openModal(): void {
    this.isModalOpen.set(true);
  }

  closeModal(): void {
    this.isModalOpen.set(false);
  }

  onSaveRole(req: CreateRoleRequest): void {
    this.roleService.createRole(req).subscribe({
      next: () => {
        this.closeModal();
        this.loadRoles();
      }
    });
  }
}

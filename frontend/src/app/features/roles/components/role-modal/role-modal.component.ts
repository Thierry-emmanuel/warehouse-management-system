import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PermissionSummary } from '../../../../core/models/user.models';
import { CreateRoleRequest } from '../../../../core/services/role.service';

@Component({
  selector: 'app-role-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './role-modal.component.html',
  styleUrls: ['./role-modal.component.css']
})
export class RoleModalComponent {
  @Input() isOpen = false;
  @Input() permissions: PermissionSummary[] = [];
  @Output() close = new EventEmitter<void>();
  @Output() save = new EventEmitter<CreateRoleRequest>();

  roleName = '';
  roleDescription = '';
  selectedPermissionIds: number[] = [];

  onClose(): void {
    this.close.emit();
  }

  togglePermission(id: number): void {
    const idx = this.selectedPermissionIds.indexOf(id);
    if (idx > -1) {
      this.selectedPermissionIds.splice(idx, 1);
    } else {
      this.selectedPermissionIds.push(id);
    }
  }

  onSubmit(): void {
    if (this.roleName.trim()) {
      this.save.emit({
        name: this.roleName.trim(),
        description: this.roleDescription.trim(),
        permissionIds: this.selectedPermissionIds
      });
      this.roleName = '';
      this.roleDescription = '';
      this.selectedPermissionIds = [];
    }
  }
}

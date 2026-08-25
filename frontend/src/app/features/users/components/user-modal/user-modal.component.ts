import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

export interface UserFormData {
  username: string;
  email: string;
  fullName: string;
  password?: string;
  phoneNumber?: string;
  warehouseId: number;
  roleIds: number[];
}

@Component({
  selector: 'app-user-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './user-modal.component.html',
  styleUrls: ['./user-modal.component.css']
})
export class UserModalComponent {
  @Input() isOpen = false;
  @Input() availableRoles: Array<{ id: number; name: string }> = [];
  @Output() close = new EventEmitter<void>();
  @Output() save = new EventEmitter<UserFormData>();

  formData: UserFormData = {
    username: '',
    email: '',
    fullName: '',
    password: '',
    phoneNumber: '',
    warehouseId: 1,
    roleIds: []
  };

  onClose(): void {
    this.close.emit();
  }

  onSubmit(): void {
    if (this.formData.username && this.formData.email && this.formData.fullName) {
      this.save.emit(this.formData);
      this.formData = { username: '', email: '', fullName: '', password: '', phoneNumber: '', warehouseId: 1, roleIds: [] };
    }
  }

  toggleRole(roleId: number): void {
    const idx = this.formData.roleIds.indexOf(roleId);
    if (idx > -1) {
      this.formData.roleIds.splice(idx, 1);
    } else {
      this.formData.roleIds.push(roleId);
    }
  }
}

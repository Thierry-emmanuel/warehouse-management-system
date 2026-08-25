import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserSummary } from '../../../../../core/models/user.models';

@Component({
  selector: 'app-admin-user-table',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-user-table.component.html',
  styleUrls: ['./admin-user-table.component.css']
})
export class AdminUserTableComponent {
  @Input() users: UserSummary[] = [];
  @Input() totalUsers = 0;
  @Input() isLoading = false;
  @Output() search = new EventEmitter<string>();
  @Output() toggleStatus = new EventEmitter<{ id: number; isActive: boolean }>();

  searchQuery = '';

  onSearch(): void {
    this.search.emit(this.searchQuery);
  }

  onToggleStatus(user: UserSummary): void {
    this.toggleStatus.emit({ id: user.id, isActive: !user.isActive });
  }

  getInitials(name: string): string {
    if (!name) return 'OP';
    return name.split(' ').map(n => n[0]).join('').substring(0, 2).toUpperCase();
  }

  getAvatarColor(name: string): string {
    const colors = ['avatar-red', 'avatar-blue', 'avatar-emerald', 'avatar-purple', 'avatar-orange'];
    let hash = 0;
    for (let i = 0; i < name.length; i++) {
      hash = name.charCodeAt(i) + ((hash << 5) - hash);
    }
    return colors[Math.abs(hash) % colors.length];
  }
}

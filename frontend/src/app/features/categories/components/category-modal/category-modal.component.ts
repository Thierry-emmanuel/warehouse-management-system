import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CategorySummary, CreateCategoryRequest } from '../../../../core/models/category.models';

@Component({
  selector: 'app-category-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './category-modal.component.html',
  styleUrls: ['./category-modal.component.css']
})
export class CategoryModalComponent {
  @Input() isOpen = false;
  @Input() categories: CategorySummary[] = [];
  @Output() close = new EventEmitter<void>();
  @Output() save = new EventEmitter<CreateCategoryRequest>();

  name = '';
  code = '';
  description = '';
  parentId: number | null = null;

  onClose(): void {
    this.close.emit();
  }

  onSubmit(): void {
    if (this.name.trim() && this.code.trim()) {
      this.save.emit({
        name: this.name.trim(),
        code: this.code.trim().toUpperCase(),
        description: this.description.trim(),
        parentId: this.parentId || undefined
      });
      this.name = '';
      this.code = '';
      this.description = '';
      this.parentId = null;
    }
  }
}

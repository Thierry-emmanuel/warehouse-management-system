import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CategoryService } from '../../core/services/category.service';
import { CategorySummary, CreateCategoryRequest } from '../../core/models/category.models';
import { CategoryModalComponent } from './components/category-modal/category-modal.component';

@Component({
  selector: 'app-category-management',
  standalone: true,
  imports: [CommonModule, FormsModule, CategoryModalComponent],
  templateUrl: './category-management.component.html',
  styleUrls: ['./category-management.component.css']
})
export class CategoryManagementComponent implements OnInit {
  private categoryService = inject(CategoryService);

  categories = signal<CategorySummary[]>([]);
  categoryTree = signal<CategorySummary[]>([]);
  isLoading = signal<boolean>(true);
  isModalOpen = signal<boolean>(false);
  searchQuery = '';

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.isLoading.set(true);
    this.categoryService.getCategories(0, 100, this.searchQuery).subscribe({
      next: (res) => {
        this.isLoading.set(false);
        if (res.success && res.data) {
          this.categories.set(res.data.content);
        }
      },
      error: () => {
        this.isLoading.set(false);
        this.categories.set([]);
      }
    });

    this.categoryService.getCategoryTree().subscribe({
      next: (res) => {
        if (res.success && res.data) {
          this.categoryTree.set(res.data);
        }
      }
    });
  }

  onSearch(): void {
    this.loadCategories();
  }

  openModal(): void {
    this.isModalOpen.set(true);
  }

  closeModal(): void {
    this.isModalOpen.set(false);
  }

  onSaveCategory(req: CreateCategoryRequest): void {
    this.categoryService.createCategory(req).subscribe({
      next: () => {
        this.closeModal();
        this.loadCategories();
      }
    });
  }

  deleteCategory(id: number): void {
    this.categoryService.deleteCategory(id).subscribe({
      next: () => this.loadCategories()
    });
  }
}

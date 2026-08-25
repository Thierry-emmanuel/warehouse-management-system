import { Component, EventEmitter, Input, Output, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CategoryService } from '../../../../core/services/category.service';
import { CategorySummary } from '../../../../core/models/category.models';
import { CreateProductPayload } from '../../../../core/services/product.service';

@Component({
  selector: 'app-product-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-modal.component.html',
  styleUrls: ['./product-modal.component.css']
})
export class ProductModalComponent {
  private categoryService = inject(CategoryService);

  @Input() isOpen = false;
  @Output() close = new EventEmitter<void>();
  @Output() save = new EventEmitter<CreateProductPayload>();

  categories = signal<CategorySummary[]>([]);

  productData: CreateProductPayload = {
    sku: '',
    name: '',
    description: '',
    barcode: '',
    categoryId: 1,
    unitOfMeasure: 'PCS',
    unitPrice: 0,
    weightKg: 1.0,
    volumeCbm: 0.05,
    minReorderLevel: 20,
    maxStockLevel: 500,
    safetyStock: 10
  };

  constructor() {
    this.categoryService.getCategories(0, 50).subscribe({
      next: (res) => {
        if (res.success && res.data) {
          this.categories.set(res.data.content);
          if (res.data.content.length > 0) {
            this.productData.categoryId = res.data.content[0].id;
          }
        }
      }
    });
  }

  onSave(): void {
    if (!this.productData.sku || !this.productData.name || !this.productData.barcode) {
      return;
    }
    this.save.emit(this.productData);
  }

  onCancel(): void {
    this.close.emit();
  }
}

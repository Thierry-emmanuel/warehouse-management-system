import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService, ProductDto, CreateProductPayload } from '../../core/services/product.service';
import { ProductModalComponent } from './components/product-modal/product-modal.component';

@Component({
  selector: 'app-product-catalog',
  standalone: true,
  imports: [CommonModule, FormsModule, ProductModalComponent],
  templateUrl: './product-catalog.component.html',
  styleUrls: ['./product-catalog.component.css']
})
export class ProductCatalogComponent implements OnInit {
  private productService = inject(ProductService);

  products = signal<ProductDto[]>([]);
  totalProducts = signal<number>(0);
  isLoading = signal<boolean>(true);
  isModalOpen = signal<boolean>(false);
  searchQuery = '';

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.isLoading.set(true);
    this.productService.getProducts(0, 50, this.searchQuery).subscribe({
      next: (res) => {
        this.isLoading.set(false);
        if (res.success && res.data) {
          this.products.set(res.data.content);
          this.totalProducts.set(res.data.totalElements);
        }
      },
      error: () => {
        this.isLoading.set(false);
      }
    });
  }

  onSearch(): void {
    this.loadProducts();
  }

  openCreateModal(): void {
    this.isModalOpen.set(true);
  }

  closeModal(): void {
    this.isModalOpen.set(false);
  }

  handleSaveProduct(payload: CreateProductPayload): void {
    this.productService.createProduct(payload).subscribe({
      next: () => {
        this.closeModal();
        this.loadProducts();
      }
    });
  }
}

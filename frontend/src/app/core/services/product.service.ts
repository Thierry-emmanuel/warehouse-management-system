import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, PagedResponse } from '../models/auth.models';

export interface ProductDto {
  id: number;
  sku: string;
  name: string;
  description?: string;
  barcode: string;
  categoryId: number;
  categoryName: string;
  unitOfMeasure: string;
  unitPrice: number;
  weightKg?: number;
  volumeCbm?: number;
  minReorderLevel: number;
  maxStockLevel: number;
  safetyStock: number;
  isActive: boolean;
}

export interface CreateProductPayload {
  sku: string;
  name: string;
  description?: string;
  barcode: string;
  categoryId: number;
  unitOfMeasure: string;
  unitPrice: number;
  weightKg?: number;
  volumeCbm?: number;
  minReorderLevel?: number;
  maxStockLevel?: number;
  safetyStock?: number;
}

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private readonly API_URL = 'http://localhost:8080/api/v1/products';

  constructor(private http: HttpClient) {}

  getProducts(page = 0, size = 20, query = '', categoryId?: number): Observable<ApiResponse<PagedResponse<ProductDto>>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (query) params = params.set('query', query);
    if (categoryId) params = params.set('categoryId', categoryId.toString());

    return this.http.get<ApiResponse<PagedResponse<ProductDto>>>(this.API_URL, { params });
  }

  createProduct(payload: CreateProductPayload): Observable<ApiResponse<ProductDto>> {
    return this.http.post<ApiResponse<ProductDto>>(this.API_URL, payload);
  }
}

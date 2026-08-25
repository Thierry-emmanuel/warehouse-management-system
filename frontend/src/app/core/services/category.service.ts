import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, PagedResponse } from '../models/auth.models';
import { CategorySummary, CreateCategoryRequest } from '../models/category.models';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private readonly BASE_URL = 'http://localhost:8080/api/v1/categories';

  constructor(private http: HttpClient) {}

  getCategories(page = 0, size = 50, query?: string): Observable<ApiResponse<PagedResponse<CategorySummary>>> {
    let params = new HttpParams().set('page', page.toString()).set('size', size.toString());
    if (query && query.trim()) params = params.set('query', query.trim());
    return this.http.get<ApiResponse<PagedResponse<CategorySummary>>>(this.BASE_URL, { params });
  }

  getCategoryTree(): Observable<ApiResponse<CategorySummary[]>> {
    return this.http.get<ApiResponse<CategorySummary[]>>(`${this.BASE_URL}/tree`);
  }

  createCategory(req: CreateCategoryRequest): Observable<ApiResponse<CategorySummary>> {
    return this.http.post<ApiResponse<CategorySummary>>(this.BASE_URL, req);
  }

  deleteCategory(id: number): Observable<ApiResponse<void>> {
    return this.http.delete<ApiResponse<void>>(`${this.BASE_URL}/${id}`);
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, PagedResponse } from '../models/auth.models';
import { PermissionSummary } from '../models/user.models';

export interface CreatePermissionRequest {
  name: string;
  description: string;
  category: string;
}

@Injectable({
  providedIn: 'root'
})
export class PermissionService {
  private readonly BASE_URL = 'http://localhost:8080/api/v1/permissions';

  constructor(private http: HttpClient) {}

  getPermissions(page = 0, size = 50, category?: string, query?: string): Observable<ApiResponse<PagedResponse<PermissionSummary>>> {
    let params = new HttpParams().set('page', page.toString()).set('size', size.toString());
    if (category && category.trim()) params = params.set('category', category.trim());
    if (query && query.trim()) params = params.set('query', query.trim());
    return this.http.get<ApiResponse<PagedResponse<PermissionSummary>>>(this.BASE_URL, { params });
  }

  createPermission(req: CreatePermissionRequest): Observable<ApiResponse<PermissionSummary>> {
    return this.http.post<ApiResponse<PermissionSummary>>(this.BASE_URL, req);
  }

  updatePermission(id: number, req: CreatePermissionRequest): Observable<ApiResponse<PermissionSummary>> {
    return this.http.put<ApiResponse<PermissionSummary>>(`${this.BASE_URL}/${id}`, req);
  }

  deletePermission(id: number): Observable<ApiResponse<void>> {
    return this.http.delete<ApiResponse<void>>(`${this.BASE_URL}/${id}`);
  }
}

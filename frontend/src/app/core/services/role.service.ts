import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, PagedResponse } from '../models/auth.models';
import { RoleSummary, PermissionSummary } from '../models/user.models';

export interface CreateRoleRequest {
  name: string;
  description: string;
  permissionIds: number[];
}

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  private readonly BASE_URL = 'http://localhost:8080/api/v1/roles';

  constructor(private http: HttpClient) {}

  getRoles(page = 0, size = 10, query?: string): Observable<ApiResponse<PagedResponse<RoleSummary>>> {
    let params = new HttpParams().set('page', page.toString()).set('size', size.toString());
    if (query && query.trim()) params = params.set('query', query.trim());
    return this.http.get<ApiResponse<PagedResponse<RoleSummary>>>(this.BASE_URL, { params });
  }

  getRoleById(id: number): Observable<ApiResponse<RoleSummary>> {
    return this.http.get<ApiResponse<RoleSummary>>(`${this.BASE_URL}/${id}`);
  }

  createRole(req: CreateRoleRequest): Observable<ApiResponse<RoleSummary>> {
    return this.http.post<ApiResponse<RoleSummary>>(this.BASE_URL, req);
  }

  updateRole(id: number, req: CreateRoleRequest): Observable<ApiResponse<RoleSummary>> {
    return this.http.put<ApiResponse<RoleSummary>>(`${this.BASE_URL}/${id}`, req);
  }

  deleteRole(id: number): Observable<ApiResponse<void>> {
    return this.http.delete<ApiResponse<void>>(`${this.BASE_URL}/${id}`);
  }
}

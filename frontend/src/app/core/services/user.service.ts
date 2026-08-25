import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, PagedResponse } from '../models/auth.models';
import { UserDetail, UserSummary } from '../models/user.models';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly BASE_URL = 'http://localhost:8080/api/v1/users';

  constructor(private http: HttpClient) {}

  getUsers(page = 0, size = 10, query?: string): Observable<ApiResponse<PagedResponse<UserSummary>>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (query && query.trim()) {
      params = params.set('query', query.trim());
    }

    return this.http.get<ApiResponse<PagedResponse<UserSummary>>>(this.BASE_URL, { params });
  }

  getUserById(id: number): Observable<ApiResponse<UserDetail>> {
    return this.http.get<ApiResponse<UserDetail>>(`${this.BASE_URL}/${id}`);
  }

  setUserStatus(id: number, isActive: boolean): Observable<ApiResponse<void>> {
    const params = new HttpParams().set('isActive', isActive.toString());
    return this.http.patch<ApiResponse<void>>(`${this.BASE_URL}/${id}/status`, null, { params });
  }
}

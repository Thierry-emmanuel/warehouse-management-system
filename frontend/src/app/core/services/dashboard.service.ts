import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '../models/auth.models';
import { AdminDashboardResponse, ManagerDashboardResponse, EmployeeDashboardResponse } from '../models/dashboard.models';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private readonly BASE_URL = 'http://localhost:8080/api/v1/dashboards';

  constructor(private http: HttpClient) {}

  getAdminDashboard(): Observable<ApiResponse<AdminDashboardResponse>> {
    return this.http.get<ApiResponse<AdminDashboardResponse>>(`${this.BASE_URL}/admin`);
  }

  getManagerDashboard(): Observable<ApiResponse<ManagerDashboardResponse>> {
    return this.http.get<ApiResponse<ManagerDashboardResponse>>(`${this.BASE_URL}/manager`);
  }

  getEmployeeDashboard(): Observable<ApiResponse<EmployeeDashboardResponse>> {
    return this.http.get<ApiResponse<EmployeeDashboardResponse>>(`${this.BASE_URL}/employee`);
  }

  getMyDashboard(): Observable<ApiResponse<unknown>> {
    return this.http.get<ApiResponse<unknown>>(`${this.BASE_URL}/my-dashboard`);
  }
}

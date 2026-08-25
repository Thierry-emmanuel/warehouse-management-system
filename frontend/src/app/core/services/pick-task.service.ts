import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, PagedResponse } from '../models/auth.models';

export interface PickTaskDto {
  id: number;
  taskCode: string;
  orderNumber: string;
  sku: string;
  productName: string;
  barcode: string;
  binLocation: string;
  targetQuantity: number;
  pickedQuantity: number;
  status: string;
  assignedOperator: string;
  sequenceOrder: number;
}

@Injectable({
  providedIn: 'root'
})
export class PickTaskService {
  private readonly API_URL = 'http://localhost:8080/api/v1/pick-tasks';

  constructor(private http: HttpClient) {}

  getPickTasks(operator?: string, page = 0, size = 50): Observable<ApiResponse<PagedResponse<PickTaskDto>>> {
    let params = new HttpParams().set('page', page.toString()).set('size', size.toString());
    if (operator) params = params.set('operator', operator);

    return this.http.get<ApiResponse<PagedResponse<PickTaskDto>>>(this.API_URL, { params });
  }

  confirmPick(taskId: number, quantity: number): Observable<ApiResponse<PickTaskDto>> {
    return this.http.post<ApiResponse<PickTaskDto>>(`${this.API_URL}/${taskId}/confirm?quantity=${quantity}`, {});
  }
}

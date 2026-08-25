import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, PagedResponse } from '../models/auth.models';

export interface InventoryItemDto {
  id: number;
  productId: number;
  productSku: string;
  productName: string;
  barcode: string;
  unitOfMeasure: string;
  locationId: number;
  locationCode: string;
  batchNumber?: string;
  expirationDate?: string;
  quantityOnHand: number;
  quantityAllocated: number;
  quantityAvailable: number;
  version: number;
}

export interface StockMovementDto {
  id: number;
  warehouseId: number;
  productSku: string;
  productName: string;
  batchNumber?: string;
  sourceLocationCode?: string;
  destinationLocationCode?: string;
  quantity: number;
  movementType: string;
  referenceType?: string;
  referenceId?: string;
  operatorUsername: string;
  timestamp: string;
}

@Injectable({
  providedIn: 'root'
})
export class InventoryService {
  private readonly API_URL = 'http://localhost:8080/api/v1/inventory';

  constructor(private http: HttpClient) {}

  getInventoryBalances(page = 0, size = 50, warehouseId?: number): Observable<ApiResponse<PagedResponse<InventoryItemDto>>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (warehouseId) params = params.set('warehouseId', warehouseId.toString());

    return this.http.get<ApiResponse<PagedResponse<InventoryItemDto>>>(this.API_URL, { params });
  }

  getStockMovements(page = 0, size = 20, warehouseId?: number): Observable<ApiResponse<PagedResponse<StockMovementDto>>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (warehouseId) params = params.set('warehouseId', warehouseId.toString());

    return this.http.get<ApiResponse<PagedResponse<StockMovementDto>>>(`${this.API_URL}/movements`, { params });
  }
}

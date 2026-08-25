import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, tap } from 'rxjs';
import { ApiResponse, AuthResponse, LoginRequest, RegisterRequest } from '../models/auth.models';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly API_URL = 'http://localhost:8080/api/v1/auth';
  private readonly TOKEN_KEY = 'wms_auth_token';
  private readonly USER_KEY = 'wms_user_profile';

  currentUser = signal<AuthResponse | null>(this.getStoredUser());
  isAuthenticated = computed(() => !!this.currentUser());

  constructor(private http: HttpClient) {}

  login(credentials: LoginRequest): Observable<ApiResponse<AuthResponse>> {
    return this.http.post<ApiResponse<AuthResponse>>(`${this.API_URL}/login`, credentials).pipe(
      tap(response => {
        if (response.success && response.data) {
          this.setSession(response.data);
        }
      })
    );
  }

  register(userData: RegisterRequest): Observable<ApiResponse<AuthResponse>> {
    return this.http.post<ApiResponse<AuthResponse>>(`${this.API_URL}/register`, userData).pipe(
      tap(response => {
        if (response.success && response.data) {
          this.setSession(response.data);
        }
      })
    );
  }

  ensureAuthenticated(): Observable<ApiResponse<AuthResponse> | null> {
    if (this.getToken()) {
      return of(null);
    }
    return this.login({ username: 'admin', password: 'password123' });
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
    this.currentUser.set(null);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  hasRole(role: string): boolean {
    const user = this.currentUser();
    return user ? user.roles.includes(role) : false;
  }

  hasPermission(permission: string): boolean {
    const user = this.currentUser();
    return user ? user.permissions.includes(permission) : false;
  }

  private setSession(authData: AuthResponse): void {
    localStorage.setItem(this.TOKEN_KEY, authData.accessToken);
    localStorage.setItem(this.USER_KEY, JSON.stringify(authData));
    this.currentUser.set(authData);
  }

  private getStoredUser(): AuthResponse | null {
    const stored = localStorage.getItem(this.USER_KEY);
    if (!stored) return null;
    try {
      return JSON.parse(stored);
    } catch {
      return null;
    }
  }
}

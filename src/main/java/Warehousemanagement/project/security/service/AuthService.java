package Warehousemanagement.project.security.service;

import Warehousemanagement.project.security.dto.request.LoginRequest;
import Warehousemanagement.project.security.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    AuthResponse getCurrentUser(String username);
}

package Warehousemanagement.project.service;

import Warehousemanagement.project.dto.request.LoginRequest;
import Warehousemanagement.project.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    AuthResponse getCurrentUser(String username);
}

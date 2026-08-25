package Warehousemanagement.project.security.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

public class UserDetailResponse {

    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private boolean isActive;
    private Long warehouseId;
    private Set<RoleSummaryResponse> roles;
    private Set<String> effectivePermissions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserDetailResponse() {
    }

    public UserDetailResponse(Long id, String username, String email, String fullName, String phoneNumber, boolean isActive, Long warehouseId, Set<RoleSummaryResponse> roles, Set<String> effectivePermissions, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
        this.warehouseId = warehouseId;
        this.roles = roles;
        this.effectivePermissions = effectivePermissions;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Set<RoleSummaryResponse> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleSummaryResponse> roles) {
        this.roles = roles;
    }

    public Set<String> getEffectivePermissions() {
        return effectivePermissions;
    }

    public void setEffectivePermissions(Set<String> effectivePermissions) {
        this.effectivePermissions = effectivePermissions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

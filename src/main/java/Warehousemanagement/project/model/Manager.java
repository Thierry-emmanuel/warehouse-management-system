package Warehousemanagement.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Managerid;
    private String Name;
    private String Username;
    private String email;
    private int Telephone;
    private String Password;
    private Boolean IsActive=true;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Manager(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    private void OnCreate(){
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
    }
    private void OnUpdate(){
        this.updatedAt=LocalDateTime.now();
    }
}

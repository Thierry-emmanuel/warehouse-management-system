package Warehousemanagement.project.repository;

import Warehousemanagement.project.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Integer>{
    Optional<Manager> findByEmail(String email);
    boolean existsByEmail(String email);
}

package Warehousemanagement.project.repository;

import Warehousemanagement.project.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer>{
    Optional<Manager> findByEmail(String email);
    boolean existsByEmail(String email);
}

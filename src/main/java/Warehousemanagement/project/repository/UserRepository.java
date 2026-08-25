package Warehousemanagement.project.repository;

import Warehousemanagement.project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    Optional<User> findWithRolesAndPermissionsByUsername(String username);

    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    Optional<User> findWithRolesAndPermissionsById(Long id);

    @EntityGraph(attributePaths = {"roles"})
    Page<User> findByWarehouseId(Long warehouseId, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.warehouseId = :warehouseId AND (" +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<User> searchUsersInWarehouse(@Param("warehouseId") Long warehouseId, @Param("query") String query, Pageable pageable);

    long countByWarehouseId(Long warehouseId);

    long countByWarehouseIdAndIsActive(Long warehouseId, boolean isActive);
}

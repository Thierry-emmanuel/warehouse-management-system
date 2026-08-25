package Warehousemanagement.project.repository;

import Warehousemanagement.project.enums.PermissionCategory;
import Warehousemanagement.project.model.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByName(String name);

    boolean existsByName(String name);

    Page<Permission> findByCategory(PermissionCategory category, Pageable pageable);

    @Query("SELECT p FROM Permission p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Permission> searchPermissions(@Param("query") String query, Pageable pageable);

    @Query("SELECT COUNT(r) FROM Role r JOIN r.permissions p WHERE p.id = :permissionId")
    long countAssignedRoles(@Param("permissionId") Long permissionId);

    @Query("SELECT p FROM Permission p WHERE p.id IN :ids")
    Set<Permission> findAllByIdIn(@Param("ids") Set<Long> ids);
}

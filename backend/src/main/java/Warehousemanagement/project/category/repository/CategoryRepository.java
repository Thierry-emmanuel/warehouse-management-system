package Warehousemanagement.project.category.repository;

import Warehousemanagement.project.category.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCode(String code);

    boolean existsByCode(String code);

    @EntityGraph(attributePaths = {"parent"})
    Optional<Category> findWithParentById(Long id);

    @EntityGraph(attributePaths = {"parent"})
    Page<Category> findAll(Pageable pageable);

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.parent WHERE " +
           "LOWER(c.code) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(c.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Category> searchCategories(@Param("query") String query, Pageable pageable);

    List<Category> findByParentId(Long parentId);

    @Query("SELECT COUNT(c) FROM Category c WHERE c.parent.id = :parentId")
    long countSubCategories(@Param("parentId") Long parentId);
}

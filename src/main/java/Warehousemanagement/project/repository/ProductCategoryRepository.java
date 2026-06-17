package Warehousemanagement.project.repository;

import Warehousemanagement.project.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,int> {
    boolean existsByProductIdAndCategoryId(Integer ProductId,Integer CategoryId);

}

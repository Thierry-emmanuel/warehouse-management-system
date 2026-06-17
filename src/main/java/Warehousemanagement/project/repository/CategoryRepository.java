package Warehousemanagement.project.repository;

import Warehousemanagement.project.model.Category;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer>
{
    boolean existsByCategoryIdAndCategoryName(Integer CategoryId,String CategoryName);
}

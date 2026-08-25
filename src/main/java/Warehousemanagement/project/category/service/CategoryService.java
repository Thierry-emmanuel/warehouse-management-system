package Warehousemanagement.project.category.service;

import Warehousemanagement.project.category.dto.request.CreateCategoryRequest;
import Warehousemanagement.project.category.dto.request.UpdateCategoryRequest;
import Warehousemanagement.project.category.dto.response.CategoryResponse;
import Warehousemanagement.project.common.dto.PagedResponse;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryResponse createCategory(CreateCategoryRequest request);

    CategoryResponse updateCategory(Long id, UpdateCategoryRequest request);

    CategoryResponse getCategoryById(Long id);

    PagedResponse<CategoryResponse> getAllCategories(String query, Pageable pageable);

    void deleteCategory(Long id);
}

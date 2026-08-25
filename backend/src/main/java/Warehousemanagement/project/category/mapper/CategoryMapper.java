package Warehousemanagement.project.category.mapper;

import Warehousemanagement.project.category.dto.request.CreateCategoryRequest;
import Warehousemanagement.project.category.dto.response.CategoryResponse;
import Warehousemanagement.project.category.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CreateCategoryRequest request, Category parent) {
        if (request == null) {
            return null;
        }
        return new Category(
            request.getCode().trim().toUpperCase(),
            request.getName().trim(),
            request.getDescription(),
            parent
        );
    }

    public CategoryResponse toResponse(Category category, long subCategoryCount) {
        if (category == null) {
            return null;
        }
        Long parentId = category.getParent() != null ? category.getParent().getId() : null;
        String parentName = category.getParent() != null ? category.getParent().getName() : null;

        return new CategoryResponse(
            category.getId(),
            category.getCode(),
            category.getName(),
            category.getDescription(),
            category.isActive(),
            parentId,
            parentName,
            subCategoryCount,
            category.getCreatedAt(),
            category.getUpdatedAt()
        );
    }
}

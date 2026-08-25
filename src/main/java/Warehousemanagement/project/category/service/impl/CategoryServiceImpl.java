package Warehousemanagement.project.category.service.impl;

import Warehousemanagement.project.category.dto.request.CreateCategoryRequest;
import Warehousemanagement.project.category.dto.request.UpdateCategoryRequest;
import Warehousemanagement.project.category.dto.response.CategoryResponse;
import Warehousemanagement.project.category.mapper.CategoryMapper;
import Warehousemanagement.project.category.model.Category;
import Warehousemanagement.project.category.repository.CategoryRepository;
import Warehousemanagement.project.category.service.CategoryService;
import Warehousemanagement.project.common.dto.PagedResponse;
import Warehousemanagement.project.common.exceptions.BusinessRuleException;
import Warehousemanagement.project.common.exceptions.DuplicateResourceException;
import Warehousemanagement.project.common.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        String normalizedCode = request.getCode().trim().toUpperCase();
        if (categoryRepository.existsByCode(normalizedCode)) {
            throw new DuplicateResourceException("Category", "code", normalizedCode);
        }

        Category parent = null;
        if (request.getParentId() != null) {
            parent = categoryRepository.findById(request.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException("Parent Category", "id", request.getParentId()));
        }

        Category category = categoryMapper.toEntity(request, parent);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved, 0L);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findWithParentById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        category.setName(request.getName().trim());
        category.setDescription(request.getDescription());

        if (request.getIsActive() != null) {
            category.setActive(request.getIsActive());
        }

        if (request.getParentId() != null) {
            if (request.getParentId().equals(id)) {
                throw new BusinessRuleException("Category cannot be its own parent");
            }
            Category parent = categoryRepository.findById(request.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException("Parent Category", "id", request.getParentId()));
            category.setParent(parent);
        } else {
            category.setParent(null);
        }

        Category updated = categoryRepository.save(category);
        long subCount = categoryRepository.countSubCategories(id);
        return categoryMapper.toResponse(updated, subCount);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findWithParentById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        long subCount = categoryRepository.countSubCategories(id);
        return categoryMapper.toResponse(category, subCount);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<CategoryResponse> getAllCategories(String query, Pageable pageable) {
        Page<Category> page;
        if (query != null && !query.trim().isEmpty()) {
            page = categoryRepository.searchCategories(query.trim(), pageable);
        } else {
            page = categoryRepository.findAll(pageable);
        }

        List<CategoryResponse> content = page.getContent().stream()
            .map(c -> {
                long subCount = categoryRepository.countSubCategories(c.getId());
                return categoryMapper.toResponse(c, subCount);
            })
            .toList();

        return PagedResponse.from(page, content);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        long subCount = categoryRepository.countSubCategories(id);
        if (subCount > 0) {
            throw new BusinessRuleException("Cannot delete category with " + subCount + " active sub-categories");
        }

        categoryRepository.delete(category);
    }
}

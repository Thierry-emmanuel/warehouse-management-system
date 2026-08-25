package Warehousemanagement.project.category;

import Warehousemanagement.project.category.dto.request.CreateCategoryRequest;
import Warehousemanagement.project.category.dto.request.UpdateCategoryRequest;
import Warehousemanagement.project.category.dto.response.CategoryResponse;
import Warehousemanagement.project.category.mapper.CategoryMapper;
import Warehousemanagement.project.category.model.Category;
import Warehousemanagement.project.category.repository.CategoryRepository;
import Warehousemanagement.project.category.service.impl.CategoryServiceImpl;
import Warehousemanagement.project.common.exceptions.BusinessRuleException;
import Warehousemanagement.project.common.exceptions.DuplicateResourceException;
import Warehousemanagement.project.common.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private CategoryMapper categoryMapper = new CategoryMapper();

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category testCategory;

    @BeforeEach
    void setUp() {
        testCategory = new Category("ELEC", "Electronics", "Electronic components and devices", null);
        testCategory.setId(1L);
    }

    @Test
    @DisplayName("Should successfully create a root category")
    void shouldCreateCategorySuccessfully() {
        CreateCategoryRequest request = new CreateCategoryRequest("ELEC", "Electronics", "Electronic devices", null);

        when(categoryRepository.existsByCode("ELEC")).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);

        CategoryResponse response = categoryService.createCategory(request);

        assertNotNull(response);
        assertEquals("ELEC", response.getCode());
        assertEquals("Electronics", response.getName());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    @DisplayName("Should reject duplicate category code")
    void shouldThrowWhenCategoryCodeExists() {
        CreateCategoryRequest request = new CreateCategoryRequest("ELEC", "Electronics", "Electronic devices", null);
        when(categoryRepository.existsByCode("ELEC")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> categoryService.createCategory(request));
        verify(categoryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should update category metadata")
    void shouldUpdateCategorySuccessfully() {
        UpdateCategoryRequest request = new UpdateCategoryRequest("Electronics & Gadgets", "Updated description", true, null);

        when(categoryRepository.findWithParentById(1L)).thenReturn(Optional.of(testCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);
        when(categoryRepository.countSubCategories(1L)).thenReturn(3L);

        CategoryResponse response = categoryService.updateCategory(1L, request);

        assertNotNull(response);
        assertEquals(3L, response.getSubCategoryCount());
        verify(categoryRepository).save(testCategory);
    }

    @Test
    @DisplayName("Should reject setting category as its own parent")
    void shouldPreventSelfReferencingParent() {
        UpdateCategoryRequest request = new UpdateCategoryRequest("Electronics", "Description", true, 1L);
        when(categoryRepository.findWithParentById(1L)).thenReturn(Optional.of(testCategory));

        assertThrows(BusinessRuleException.class, () -> categoryService.updateCategory(1L, request));
    }

    @Test
    @DisplayName("Should prevent deleting category with active sub-categories")
    void shouldPreventDeletingCategoryWithSubCategories() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        when(categoryRepository.countSubCategories(1L)).thenReturn(2L);

        assertThrows(BusinessRuleException.class, () -> categoryService.deleteCategory(1L));
        verify(categoryRepository, never()).delete(any());
    }
}

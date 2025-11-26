package com.casarick.api.service.imp;

import com.casarick.api.dto.CategoryDTO;
import com.casarick.api.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<CategoryDTO> getCategories();
    CategoryDTO getCategoryById(Long id);
    Optional<CategoryDTO> getCategoryByName(String name);
    CategoryDTO createCategory(Category category);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
}

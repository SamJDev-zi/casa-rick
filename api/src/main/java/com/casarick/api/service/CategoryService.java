package com.casarick.api.service;

import com.casarick.api.dto.CategoryDTO;
import com.casarick.api.exception.NotFoundException;
import com.casarick.api.mapper.Mapper;
import com.casarick.api.model.Category;
import com.casarick.api.reposiroty.CategoryRepository;
import com.casarick.api.service.imp.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository repository;

    @Override
    public List<CategoryDTO> getCategories() {
        return repository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " +id));

        return Mapper.toDTO(category);
    }

    @Override
    public Optional<CategoryDTO> getCategoryByName(String name) {
        Optional<Category> categoryOptional = repository.getCategoryByName(name);
        return categoryOptional.map(Mapper::toDTO);
    }

    @Override
    public CategoryDTO createCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("ClotheType object cannot be null for creation.");
        }
        return Mapper.toDTO(repository.save(category));
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));

        category.setName(categoryDTO.getName());
        return Mapper.toDTO(repository.save(category));
    }
}

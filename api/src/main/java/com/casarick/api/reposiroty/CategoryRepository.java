package com.casarick.api.reposiroty;

import com.casarick.api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> getCategoryByName(String name);
}

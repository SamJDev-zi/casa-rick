package com.casarick.api.reposiroty;

import com.casarick.api.model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
}

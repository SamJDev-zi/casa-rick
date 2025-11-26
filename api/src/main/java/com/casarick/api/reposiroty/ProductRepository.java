package com.casarick.api.reposiroty;

import com.casarick.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> getProductByBarCode(String barCode);
}

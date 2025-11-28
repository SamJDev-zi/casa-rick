package com.casarick.api.service.imp;

import com.casarick.api.dto.ProductRequestDTO;
import com.casarick.api.dto.ProductResponseDTO;
import com.casarick.api.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<ProductResponseDTO> getProduct();
    ProductResponseDTO getProductById(Long id);
    ProductResponseDTO getProductByCategory(Long id, Long categoryId);
    Optional<ProductResponseDTO> getProductByBarCode(String barCode);
    ProductResponseDTO createProduct(ProductRequestDTO Product);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO);
    void deleteProduct(Long id);
}

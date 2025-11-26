package com.casarick.api.service.imp;

import com.casarick.api.dto.ProductDTO;
import com.casarick.api.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<ProductDTO> getProduct();
    ProductDTO getProductById(Long id);
    ProductDTO getProductByCategory(Long id, Long categoryId);
    Optional<ProductDTO> getProductByBarCode(String barCode);
    ProductDTO createProduct(Product Product);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
}

package com.casarick.api.service;

import com.casarick.api.dto.ProductDTO;
import com.casarick.api.exception.NotFoundException;
import com.casarick.api.mapper.Mapper;
import com.casarick.api.model.Category;
import com.casarick.api.model.Product;
import com.casarick.api.reposiroty.CategoryRepository;
import com.casarick.api.reposiroty.ProductRepository;
import com.casarick.api.service.imp.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductDTO> getProduct() {
        return productRepository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        return Mapper.toDTO(product);
    }

    @Override
    public ProductDTO getProductByCategory(Long id, Long categoryId) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        return Mapper.toDTO(product);
    }

    @Override
    public Optional<ProductDTO> getProductByBarCode(String barCode) {
        Optional<Product> productOptional = productRepository.getProductByBarCode(barCode);
        return productOptional.map(Mapper::toDTO);
    }

    @Override
    public ProductDTO createProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("ClotheType object cannot be null for creation.");
        }

        return Mapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        product.setName(productDTO.getName());
        product.setCategory(Mapper.toEntity(productDTO.getCategoryDTO()));
        product.setClotheType(Mapper.toEntity(productDTO.getClotheTypeDTO()));
        product.setIndustry(Mapper.toEntity(productDTO.getIndustryDTO()));
        product.setColor(productDTO.getColor());
        product.setSize(product.getSize());
        product.setPhotoURL(product.getPhotoURL());
        return Mapper.toDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));

        productRepository.delete(product);
    }
}

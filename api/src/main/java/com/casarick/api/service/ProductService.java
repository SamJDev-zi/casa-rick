package com.casarick.api.service;

import com.casarick.api.dto.ProductRequestDTO;
import com.casarick.api.dto.ProductResponseDTO;
import com.casarick.api.exception.NotFoundException;
import com.casarick.api.mapper.Mapper;
import com.casarick.api.model.Category;
import com.casarick.api.model.ClotheType;
import com.casarick.api.model.Industry;
import com.casarick.api.model.Product;
import com.casarick.api.reposiroty.CategoryRepository;
import com.casarick.api.reposiroty.ClotheTypeRepository;
import com.casarick.api.reposiroty.IndustryRepository;
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
    @Autowired
    private ClotheTypeRepository clotheTypeRepository;
    @Autowired
    private IndustryRepository industryRepository;

    @Override
    public List<ProductResponseDTO> getProduct() {
        return productRepository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        return Mapper.toDTO(product);
    }

    @Override
    public ProductResponseDTO getProductByCategory(Long id, Long categoryId) {
        Product product = productRepository.findByIdAndCategoryId(id, categoryId)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id +
                        " in Category ID: " + categoryId));

        return Mapper.toDTO(product);
    }

    @Override
    public Optional<ProductResponseDTO> getProductByBarCode(String barCode) {
        Optional<Product> productOptional = productRepository.getProductByBarCode(barCode);
        return productOptional.map(Mapper::toDTO);
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productDTO) {
        if (productDTO == null) {
            throw new IllegalArgumentException("ClotheType object cannot be null for creation.");
        }

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        ClotheType type = clotheTypeRepository.findById(productDTO.getClotheTypeId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        Industry industry = industryRepository.findById(productDTO.getIndustryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        Product product = Mapper.toEntity(productDTO, category, type, industry);

        return Mapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        Category category = categoryRepository.findById(productRequestDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        ClotheType type = clotheTypeRepository.findById(productRequestDTO.getClotheTypeId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        Industry industry = industryRepository.findById(productRequestDTO.getIndustryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        product.setName(productRequestDTO.getName());
        product.setCategory(category);
        product.setClotheType(type);
        product.setIndustry(industry);
        product.setColor(productRequestDTO.getColor());
        product.setSize(productRequestDTO.getSize());
        product.setPhotoURL(productRequestDTO.getPhotoURL());
        return Mapper.toDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));

        productRepository.delete(product);
    }
}

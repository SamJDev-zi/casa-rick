package com.casarick.api.controller;

import com.casarick.api.dto.ProductRequestDTO;
import com.casarick.api.dto.ProductResponseDTO;
import com.casarick.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getProduct());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/{id}/search/category/{categoryId}")
    public ResponseEntity<ProductResponseDTO> getProductByCategory(
            @RequestParam Long id,
            @PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductByCategory(id, categoryId));
    }

    @GetMapping("/barcode/{barCode}")
    public ResponseEntity<ProductResponseDTO> getProductByBarCode(@PathVariable String barCode) {
        return productService.getProductByBarCode(barCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO product) {
        ProductResponseDTO createdDTO = productService.createProduct(product);
        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO updatedDTO = productService.updateProduct(id, productRequestDTO);
        return ResponseEntity.ok(updatedDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
package com.casarick.api.controller;

import com.casarick.api.dto.SaleRequestDTO;
import com.casarick.api.dto.SaleResponseDTO;
import com.casarick.api.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping
    public ResponseEntity<List<SaleResponseDTO>> getAllSales() {
        return ResponseEntity.ok(saleService.getSales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> getSaleById(@PathVariable Long id) {
        return ResponseEntity.ok(saleService.getSaleById(id));
    }

    @PostMapping
    public ResponseEntity<SaleResponseDTO> createSale(@RequestBody SaleRequestDTO saleResponseDTO) {
        SaleResponseDTO createdDTO = saleService.createSale(saleResponseDTO);
        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> updateSale(@PathVariable Long id, @RequestBody SaleResponseDTO saleResponseDTO) {
        SaleResponseDTO updatedDTO = saleService.updateSale(id, saleResponseDTO);
        return ResponseEntity.ok(updatedDTO);
    }
}
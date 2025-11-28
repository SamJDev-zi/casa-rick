package com.casarick.api.controller;

import com.casarick.api.dto.InventoryRequestDTO;
import com.casarick.api.dto.InventoryResponseDTO;
import com.casarick.api.model.Inventory;
import com.casarick.api.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getAllInventories() {
        return ResponseEntity.ok(inventoryService.getAllInventories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> getInventoryById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getInventoryById(id));
    }

    @PostMapping()
    public ResponseEntity<InventoryResponseDTO> createInventory(@RequestBody InventoryRequestDTO inventory) {

        InventoryResponseDTO createdDTO = inventoryService.createInventory(inventory);

        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }

    @GetMapping("/profit")
    public ResponseEntity<BigDecimal> calculateTotalApproximateProfit() {
        BigDecimal profit = inventoryService.calculateAproximateProfit();
        return ResponseEntity.ok(profit);
    }
}
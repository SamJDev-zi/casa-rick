package com.casarick.api.service.imp;

import com.casarick.api.dto.InventoryRequestDTO;
import com.casarick.api.dto.InventoryResponseDTO;
import com.casarick.api.model.Inventory;

import java.math.BigDecimal;
import java.util.List;

public interface IInventoryService {
    List<InventoryResponseDTO> getAllInventories();
    InventoryResponseDTO getInventoryById(Long id);
    InventoryResponseDTO createInventory(InventoryRequestDTO inventory);
    InventoryResponseDTO updateInventory(Long id, InventoryRequestDTO inventoryRequestDTO);
    BigDecimal calculateAproximateProfit();
    void deleteInventory(Long id);
}

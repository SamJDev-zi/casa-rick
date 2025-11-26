package com.casarick.api.service.imp;

import com.casarick.api.dto.InventoryDTO;
import com.casarick.api.model.Inventory;
import com.casarick.api.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface IInventoryService {
    List<InventoryDTO> getAllInventories();
    InventoryDTO getInventoryById(Long id);
    InventoryDTO createInventory(Inventory inventory, Long productId);
    InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO);
    BigDecimal calculateAproximateProfit();
    void deleteInventory(Long id);
}

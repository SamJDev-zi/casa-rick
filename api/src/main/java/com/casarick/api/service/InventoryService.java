package com.casarick.api.service;

import com.casarick.api.dto.InventoryRequestDTO;
import com.casarick.api.dto.InventoryResponseDTO;
import com.casarick.api.exception.NotFoundException;
import com.casarick.api.mapper.Mapper;
import com.casarick.api.model.Branch;
import com.casarick.api.model.Inventory;
import com.casarick.api.model.Product;
import com.casarick.api.reposiroty.BranchRepository;
import com.casarick.api.reposiroty.InventoryRepository;
import com.casarick.api.reposiroty.ProductRepository;
import com.casarick.api.service.imp.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryService implements IInventoryService {

    @Autowired
    private InventoryRepository repository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Override
    public List<InventoryResponseDTO> getAllInventories() {
        return repository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public InventoryResponseDTO getInventoryById(Long id) {
        Inventory inventory = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Inventory not found with id: "+ id));
        return Mapper.toDTO(inventory);
    }

    @Override
    public InventoryResponseDTO createInventory(InventoryRequestDTO inventoryDTO) {
        if (inventoryDTO == null) {
            throw new IllegalArgumentException("Inventory object cannot be null for creation.");
        }

        Product product = productRepository.findById(inventoryDTO.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + inventoryDTO.getProductId()));

        Branch branch = branchRepository.findById(inventoryDTO.getBranchId())
                .orElseThrow(() -> new NotFoundException("Branch not found with id: " + inventoryDTO.getProductId()));

        Inventory inventory = Mapper.toEntity(inventoryDTO, product, branch);

        return Mapper.toDTO(repository.save(inventory));
    }

    @Override
    public InventoryResponseDTO updateInventory(Long id, InventoryRequestDTO inventoryRequestDTO) {
        if (inventoryRequestDTO == null) {
            throw new IllegalArgumentException("Inventory object cannot be null for creation.");
        }

        Inventory inventory = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Inventory not found with id: "+ id));

        inventory.setUpdated(LocalDateTime.now());

        return Mapper.toDTO(repository.save(inventory));
    }

    @Override
    public BigDecimal calculateAproximateProfit() {
        BigDecimal totalProfit = repository.getTotalApproximateProfit();
        return totalProfit != null ? totalProfit : BigDecimal.ZERO;
    }

    @Override
    public void deleteInventory(Long id) {
        Inventory inventory = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Inventory not found with id: "+ id));

        repository.delete(inventory);
    }
}

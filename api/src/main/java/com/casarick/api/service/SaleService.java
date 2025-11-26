package com.casarick.api.service;

import com.casarick.api.dto.InventoryDTO;
import com.casarick.api.dto.SaleDTO;
import com.casarick.api.exception.NotFoundException;
import com.casarick.api.mapper.Mapper;
import com.casarick.api.model.*;
import com.casarick.api.reposiroty.*;
import com.casarick.api.service.imp.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService implements ISaleService {

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Override
    public List<SaleDTO> getSales() {
        return saleRepository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public SaleDTO getSaleById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Branch not found with id: " + id));

        return Mapper.toDTO(sale);
    }

    @Override
    public SaleDTO createSale (SaleDTO saleDTO) {
        if (saleDTO == null) {
            throw new IllegalArgumentException("Sale data cannot be null.");
        }

        Long customerId = saleDTO.getCustomerDTO().getId();
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found ID: " + customerId));

        Long employeeId = saleDTO.getEmployeeDTO().getId();
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found ID: " + employeeId));

        Long branchId = saleDTO.getBranchDTO().getId();
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new NotFoundException("Branch not found ID: " + branchId));

        List<Inventory> inventoriesToSave = new ArrayList<>();
        List<Inventory> saleInventories = new ArrayList<>();

        BigDecimal subtotalAmount = BigDecimal.ZERO;
        int totalQuantityItems = 0;

        if (saleDTO.getInventoryDTOList() != null) {
            for (InventoryDTO itemDTO : saleDTO.getInventoryDTOList()) {

                Inventory dbInventory = inventoryRepository.findById(itemDTO.getId())
                        .orElseThrow(() -> new NotFoundException("Inventory item not found ID: " + itemDTO.getId()));

                int quantityToSell = itemDTO.getStock();

                if (dbInventory.getStock() < quantityToSell) {
                    throw new IllegalArgumentException("Insufficient stock for Product: " +
                            dbInventory.getProduct().getName() +
                            ". Available: " + dbInventory.getStock() + ", Requested: " + quantityToSell);
                }

                dbInventory.setStock(dbInventory.getStock() - quantityToSell);
                inventoriesToSave.add(dbInventory);

                saleInventories.add(dbInventory);

                BigDecimal itemTotal = dbInventory.getSalePrice().multiply(BigDecimal.valueOf(quantityToSell));
                subtotalAmount = subtotalAmount.add(itemTotal);

                totalQuantityItems += quantityToSell;
            }
        }

        inventoryRepository.saveAll(inventoriesToSave);

        BigDecimal discount = saleDTO.getDiscount() != null ? saleDTO.getDiscount() : BigDecimal.ZERO;
        BigDecimal totalFinal = subtotalAmount.subtract(discount);

        if (totalFinal.compareTo(BigDecimal.ZERO) < 0) totalFinal = BigDecimal.ZERO;


        Sale sale = Sale.builder()
                .description(saleDTO.getDescription())
                .quantity(totalQuantityItems)
                .amount(subtotalAmount)
                .discount(discount)
                .total(totalFinal)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .customer(customer)
                .employee(employee)
                .branch(branch)
                .inventoryList(saleInventories)
                .build();

        Sale savedSale = saleRepository.save(sale);
        return Mapper.toDTO(savedSale);
    }

    @Override
    public SaleDTO updateSale(Long id, SaleDTO saleDTO) {
        // 1. Buscar la venta existente
        Sale existingSale = saleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sale not found with id: " + id));

        existingSale.setDescription(saleDTO.getDescription());

        if (saleDTO.getCustomerDTO() != null && saleDTO.getCustomerDTO().getId() != null) {
            Customer newCustomer = customerRepository.findById(saleDTO.getCustomerDTO().getId())
                    .orElseThrow(() -> new NotFoundException("Customer not found"));
            existingSale.setCustomer(newCustomer);
        }

        if (saleDTO.getDiscount() != null) {
            existingSale.setDiscount(saleDTO.getDiscount());

            BigDecimal newTotal = existingSale.getAmount().subtract(saleDTO.getDiscount());

            if (newTotal.compareTo(BigDecimal.ZERO) < 0) newTotal = BigDecimal.ZERO;

            existingSale.setTotal(newTotal);
        }

        existingSale.setUpdated(LocalDateTime.now());

        return Mapper.toDTO(saleRepository.save(existingSale));
    }
}

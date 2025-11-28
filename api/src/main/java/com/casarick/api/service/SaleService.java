package com.casarick.api.service;

import com.casarick.api.dto.SaleRequestDTO;
import com.casarick.api.dto.SaleResponseDTO;
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
    public List<SaleResponseDTO> getSales() {
        return saleRepository.findAllWithInventory().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public SaleResponseDTO getSaleById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Branch not found with id: " + id));

        return Mapper.toDTO(sale);
    }

    @Override
    public SaleResponseDTO createSale (SaleRequestDTO saleResponseDTO) {
        if (saleResponseDTO == null) {
            throw new IllegalArgumentException("Sale data cannot be null.");
        }

        Customer customer = customerRepository.findById(saleResponseDTO.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Customer not found ID: " + saleResponseDTO.getCustomerId()));

        Employee employee = employeeRepository.findById(saleResponseDTO.getEmployeeId())
                .orElseThrow(() -> new NotFoundException("Employee not found ID: " + saleResponseDTO.getEmployeeId()));

        Branch branch = branchRepository.findById(saleResponseDTO.getBranchId())
                .orElseThrow(() -> new NotFoundException("Branch not found ID: " + saleResponseDTO.getBranchId()));

        List<Inventory> inventoriesToSave = new ArrayList<>();
        List<Inventory> saleInventories = new ArrayList<>();

        BigDecimal subtotalAmount = BigDecimal.ZERO;
        int totalQuantityItems = 0;

        if (saleResponseDTO.getInventoryList() != null) {
            for (Inventory itemDTO : saleResponseDTO.getInventoryList()) {

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

        BigDecimal discount = saleResponseDTO.getDiscount() != null ? saleResponseDTO.getDiscount() : BigDecimal.ZERO;
        BigDecimal totalFinal = subtotalAmount.subtract(discount);

        if (totalFinal.compareTo(BigDecimal.ZERO) < 0) totalFinal = BigDecimal.ZERO;


        Sale sale = Sale.builder()
                .description(saleResponseDTO.getDescription())
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
    public SaleResponseDTO updateSale(Long id, SaleResponseDTO saleResponseDTO) {
        Sale existingSale = saleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sale not found with id: " + id));

        existingSale.setDescription(saleResponseDTO.getDescription());

        if (saleResponseDTO.getCustomerDTO() != null && saleResponseDTO.getCustomerDTO().getId() != null) {
            Customer newCustomer = customerRepository.findById(saleResponseDTO.getCustomerDTO().getId())
                    .orElseThrow(() -> new NotFoundException("Customer not found"));
            existingSale.setCustomer(newCustomer);
        }

        if (saleResponseDTO.getDiscount() != null) {
            existingSale.setDiscount(saleResponseDTO.getDiscount());

            BigDecimal newTotal = existingSale.getAmount().subtract(saleResponseDTO.getDiscount());

            if (newTotal.compareTo(BigDecimal.ZERO) < 0) newTotal = BigDecimal.ZERO;

            existingSale.setTotal(newTotal);
        }

        existingSale.setUpdated(LocalDateTime.now());

        return Mapper.toDTO(saleRepository.save(existingSale));
    }
}

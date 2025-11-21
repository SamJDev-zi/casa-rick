package com.casarick.api.mapper;

import com.casarick.api.dto.*;
import com.casarick.api.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    private static ManagerDTO toDTO(Manager manager) {
        if (manager != null) {
            return ManagerDTO.builder()
                    .id(manager.getId())
                    .name(manager.getName())
                    .lastName(manager.getLastName())
                    .build();
        }
        return null;
    }

    private static BranchDTO toDTO(Branch branch) {
        if (branch != null) {
            return BranchDTO.builder()
                    .id(branch.getId())
                    .name(branch.getName())
                    .address(branch.getAddress())
                    .isActive(branch.isActive())
                    .manager(toDTO(branch.getManager()))
                    .phoneNumber(branch.getPhoneNumber())
                    .build();
        }
        return null;
    }

    private static CategoryDTO toDTO(Category category) {
        if (category != null) {
            return CategoryDTO.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .build();
        }
        return null;
    }

    private static ClotheTypeDTO toDTO(ClotheType clotheType) {
        if (clotheType != null) {
            return ClotheTypeDTO.builder()
                    .id(clotheType.getId())
                    .name(clotheType.getName())
                    .build();
        }
        return null;
    }

    private static ProductVariantDTO toDTO(ProductVariant variant) {
        if (variant != null) {
            return ProductVariantDTO.builder()
                    .id(variant.getId())
                    .categoryDTO(toDTO(variant.getCategory()))
                    .clotheTypeDTO(toDTO(variant.getClotheType()))
                    .industryDTO(toDTO(variant.getIndustry()))
                    .build();
        }
        return null;
    }

    private static IndustryDTO toDTO(Industry industry) {
        if (industry != null) {
            return IndustryDTO.builder()
                    .id(industry.getId())
                    .name(industry.getName())
                    .build();
        }
        return null;
    }

    private static ProductDTO toDTO(Product product) {
        if (product != null) {
            return ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .productVariantDTO(toDTO(product.getVariant()))
                    .photoURL(product.getPhotoURL())
                    .barCode(product.getBarCode())
                    .branchDTO(toDTO(product.getBranch()))
                    .build();
        }
        return null;
    }

    private static EmployeeDTO toDTO(Employee employee) {
        if (employee != null) {

            List<PermissionDTO> permissionDTOs = employee.getPermissionList().stream()
                    .map(Mapper::toDTO)
                    .toList();

            return EmployeeDTO.builder()
                    .id(employee.getId())
                    .name(employee.getName())
                    .lastName(employee.getLastName())
                    .isActive(employee.isActive())
                    .phonNumber(employee.getPhoneNumber())
                    .permissionDTOList(permissionDTOs)
                    .branchDTO(toDTO(employee.getBranch()))
                    .build();
        }
        return null;
    }

    private static PermissionDTO toDTO(Permission permission) {
        if (permission != null) {
            return PermissionDTO.builder()
                    .id(permission.getId())
                    .name(permission.getName())
                    .build();
        }
        return null;
    }

    private static Employee toEntity(EmployeeRequestDTO employeeRequestDTO, List<Permission> permissions, Branch branch) {
        if (employeeRequestDTO != null) {
            return Employee.builder()
                    .name(employeeRequestDTO.getName())
                    .lastName(employeeRequestDTO.getLastName())
                    .phoneNumber(employeeRequestDTO.getPhonNumber())
                    .password(employeeRequestDTO.getPassword())
                    .permissionList(permissions)
                    .branch(branch)
                    .build();
        }
        return null;
    }

    private static Manager toEntity(ManagerRequestDTO managerDTO) {
        if (managerDTO != null) {
            return Manager.builder()
                    .name(managerDTO.getName())
                    .lastName(managerDTO.getLastName())
                    .password(managerDTO.getPassword())
                    .build();
        }
        return null;
    }

    private static SaleDTO toDTO(Sale sale) {
        if (sale != null) {
            List<InventoryDTO> inventoryDTOList = sale.getInventoryList().stream()
                    .map(Mapper::toDTO)
                    .collect(Collectors.toList());

            return SaleDTO.builder()
                    .id(sale.getId())
                    .description(sale.getDescription())
                    .quantity(sale.getQuantity())
                    .amount(sale.getAmount())
                    .discount(sale.getDiscount())
                    .total(sale.getTotal())
                    .created(sale.getCreated())
                    .updated(sale.getUpdated())
                    .customerDTO(toDTO(sale.getCustomer()))
                    .employeeDTO(toDTO(sale.getEmployee()))
                    .branchDTO(toDTO(sale.getBranch()))
                    .inventoryDTOList(inventoryDTOList)
                    .build();
        }
        return null;
    }

    private static InventoryDTO toDTO(Inventory inventory) {
        if (inventory != null) {
            BigDecimal unitProfit = inventory.getSalePrice().subtract(inventory.getCostPrice());

            BigDecimal totalProfit = unitProfit.multiply(BigDecimal.valueOf(inventory.getStock()));

            return InventoryDTO.builder()
                    .id(inventory.getId())
                    .productDTO(toDTO(inventory.getProduct()))
                    .stock(inventory.getStock())
                    .costPrice(inventory.getCostPrice())
                    .salePrice(inventory.getSalePrice())
                    .created(inventory.getCreated())
                    .updated(inventory.getUpdated())
                    .branchDTO(toDTO(inventory.getBranch()))
                    .aproximateProfit(totalProfit)
                    .build();
        }
        return null;
    }

    private static CustomerDTO toDTO(Customer customer) {
        if (customer != null) {
            return CustomerDTO.builder()
                    .id(customer.getId())
                    .name(customer.getName())
                    .lastName(customer.getLastName())
                    .phoneNumber(customer.getPhoneNumber())
                    .build();
        }
        return null;
    }

    private static ReservationDTO toDTO(Reservation reservation) {
        if (reservation != null) {
            List<InventoryDTO> inventoryDTOList = reservation.getInventoryList().stream()
                    .map(Mapper::toDTO)
                    .collect(Collectors.toList());

            return ReservationDTO.builder()
                    .id(reservation.getId())
                    .description(reservation.getDescription())
                    .amount(reservation.getAmount())
                    .balance(reservation.getBalance())
                    .status(reservation.getStatus())
                    .quantity(reservation.getQuantity())
                    .created(reservation.getCreated())
                    .expirationDate(reservation.getExpirationDate())
                    .updated(reservation.getUpdated())
                    .customerDTO(toDTO(reservation.getCustomer()))
                    .employeeDTO(toDTO(reservation.getEmployee()))
                    .branchDTO(toDTO(reservation.getBranch()))
                    .inventoryDTOList(inventoryDTOList)
                    .build();
        }
        return null;
    }
}

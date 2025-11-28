package com.casarick.api.mapper;

import com.casarick.api.dto.*;
import com.casarick.api.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public static ManagerResponseDTO toDTO(Manager manager) {
        if (manager != null) {
            return ManagerResponseDTO.builder()
                    .id(manager.getId())
                    .name(manager.getName())
                    .lastName(manager.getLastName())
                    .build();
        }
        return null;
    }

    public static Manager toEntity(ManagerRequestDTO managerRequestDTO) {
        if (managerRequestDTO != null) {
            return Manager.builder()
                    .name(managerRequestDTO.getName())
                    .lastName(managerRequestDTO.getLastName())
                    .password(managerRequestDTO.getPassword())
                    .build();
        }
        return null;
    }

    public static BranchResponseDTO toDTO(Branch branch) {
        if (branch != null) {
            return BranchResponseDTO.builder()
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
    public static Branch toEntity(BranchRequestDTO branchRequestDTO, Manager manager) {
        if (branchRequestDTO != null) {
            return Branch.builder()
                    .name(branchRequestDTO.getName())
                    .address(branchRequestDTO.getAddress())
                    .isActive(branchRequestDTO.isActive())
                    .manager(manager)
                    .build();
        }
        return null;
    }

    public static CategoryDTO toDTO(Category category) {
        if (category != null) {
            return CategoryDTO.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .build();
        }
        return null;
    }

    public static ClotheTypeDTO toDTO(ClotheType clotheType) {
        if (clotheType != null) {
            return ClotheTypeDTO.builder()
                    .id(clotheType.getId())
                    .name(clotheType.getName())
                    .build();
        }
        return null;
    }

    public static IndustryDTO toDTO(Industry industry) {
        if (industry != null) {
            return IndustryDTO.builder()
                    .id(industry.getId())
                    .name(industry.getName())
                    .build();
        }
        return null;
    }

    public static PermissionDTO toDTO(Permission permission) {
        if (permission != null) {
            return PermissionDTO.builder()
                    .id(permission.getId())
                    .name(permission.getName())
                    .build();
        }
        return null;
    }

    public static CustomerDTO toDTO(Customer customer) {
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

    public static ProductResponseDTO toDTO(Product product) {
        if (product != null) {
            return ProductResponseDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .categoryDTO(toDTO(product.getCategory()))
                    .clotheTypeDTO(toDTO(product.getClotheType()))
                    .industryDTO(toDTO(product.getIndustry()))
                    .color(product.getColor())
                    .size(product.getSize())
                    .photoURL(product.getPhotoURL())
                    .barCode(product.getBarCode())
                    .build();
        }
        return null;
    }

    public static Product toEntity(ProductRequestDTO productRequestDTO, Category category, ClotheType type, Industry industry) {
        if (productRequestDTO != null) {
            return Product.builder()
                    .id(productRequestDTO.getId())
                    .name(productRequestDTO.getName())
                    .category(category)
                    .clotheType(type)
                    .industry(industry)
                    .color(productRequestDTO.getColor())
                    .size(productRequestDTO.getSize())
                    .photoURL(productRequestDTO.getPhotoURL())
                    .barCode(productRequestDTO.getBarCode())
                    .build();
        }
        return null;
    }

    //Revisar
    public static EmployeeResponseDTO toDTO(Employee employee) {
        if (employee != null) {

            List<PermissionDTO> permissionDTOs = employee.getPermissionList().stream()
                    .map(Mapper::toDTO)
                    .toList();

            return EmployeeResponseDTO.builder()
                    .id(employee.getId())
                    .name(employee.getName())
                    .lastName(employee.getLastName())
                    .isActive(employee.isActive())
                    .phonNumber(employee.getPhoneNumber())
                    .permissionDTOList(permissionDTOs)
                    .branch(toDTO(employee.getBranch()))
                    .build();
        }
        return null;
    }

    public static Employee toEntity(EmployeeRequestDTO employeeRequestDTO, List<Permission> permissions, Branch branch) {
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
    //Revisar

    public static SaleResponseDTO toDTO(Sale sale) {
        if (sale != null) {
            List<InventoryResponseDTO> inventoryRequestDTOList = sale.getInventoryList().stream()
                    .map(Mapper::toDTO)
                    .collect(Collectors.toList());

            return SaleResponseDTO.builder()
                    .id(sale.getId())
                    .description(sale.getDescription())
                    .quantity(sale.getQuantity())
                    .amount(sale.getAmount())
                    .discount(sale.getDiscount())
                    .total(sale.getTotal())
                    .created(sale.getCreated())
                    .updated(sale.getUpdated())
                    .customerDTO(toDTO(sale.getCustomer()))
                    .employeeResponseDTO(toDTO(sale.getEmployee()))
                    .branch(toDTO(sale.getBranch()))
                    .inventoryList(inventoryRequestDTOList)
                    .build();
        }
        return null;
    }

    public static Sale toEntity(SaleRequestDTO saleRequestDTO, Customer customer, Employee employee, Branch branch, List<Inventory> inventoryList) {
        if (saleRequestDTO != null) {

            return Sale.builder()
                    .id(saleRequestDTO.getId())
                    .description(saleRequestDTO.getDescription())
                    .quantity(saleRequestDTO.getQuantity())
                    .amount(saleRequestDTO.getAmount())
                    .discount(saleRequestDTO.getDiscount())
                    .total(saleRequestDTO.getTotal())
                    .created(saleRequestDTO.getCreated())
                    .updated(saleRequestDTO.getUpdated())
                    .customer(customer)
                    .employee(employee)
                    .branch(branch)
                    .inventoryList(inventoryList)
                    .build();
        }
        return null;
    }

    public static InventoryResponseDTO toDTO(Inventory inventory) {
        if (inventory != null) {
            BigDecimal unitProfit = inventory.getSalePrice().subtract(inventory.getCostPrice());

            BigDecimal totalProfit = unitProfit.multiply(BigDecimal.valueOf(inventory.getStock()));

            return InventoryResponseDTO.builder()
                    .id(inventory.getId())
                    .productRequestDTO(toDTO(inventory.getProduct()))
                    .stock(inventory.getStock())
                    .costPrice(inventory.getCostPrice())
                    .salePrice(inventory.getSalePrice())
                    .created(inventory.getCreated())
                    .updated(inventory.getUpdated())
                    .branch(toDTO(inventory.getBranch()))
                    .aproximateProfit(totalProfit)
                    .build();
        }
        return null;
    }

    public static Inventory toEntity(InventoryRequestDTO inventoryRequestDTO, Product product, Branch branch) {
        if (product != null) {
            return Inventory.builder()
                    .id(inventoryRequestDTO.getId())
                    .product(product)
                    .stock(inventoryRequestDTO.getStock())
                    .costPrice(inventoryRequestDTO.getCostPrice())
                    .salePrice(inventoryRequestDTO.getSalePrice())
                    .created(inventoryRequestDTO.getCreated())
                    .updated(inventoryRequestDTO.getUpdated())
                    .branch(branch)
                    .build();
        }
        return null;
    }

    public static ReservationResponseDTO toDTO(Reservation reservation) {
        if (reservation != null) {
            List<InventoryResponseDTO> inventoryList = reservation.getInventoryList().stream()
                    .map(Mapper::toDTO)
                    .collect(Collectors.toList());

            return ReservationResponseDTO.builder()
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
                    .employeeResponseDTO(toDTO(reservation.getEmployee()))
                    .branch(toDTO(reservation.getBranch()))
                    .inventoryList(inventoryList)
                    .build();
        }
        return null;
    }

    public static Reservation toEntity(ReservationRequestDTO reservationRequestDTO, Customer customer, Employee employee, Branch branch, List<Inventory> inventoryList) {
        if (reservationRequestDTO != null) {

            return Reservation.builder()
                    .id(reservationRequestDTO.getId())
                    .description(reservationRequestDTO.getDescription())
                    .amount(reservationRequestDTO.getAmount())
                    .balance(reservationRequestDTO.getBalance())
                    .status(reservationRequestDTO.getStatus())
                    .quantity(reservationRequestDTO.getQuantity())
                    .created(reservationRequestDTO.getCreated())
                    .expirationDate(reservationRequestDTO.getExpirationDate())
                    .updated(reservationRequestDTO.getUpdated())
                    .customer(customer)
                    .employee(employee)
                    .branch(branch)
                    .inventoryList(inventoryList)
                    .build();
        }
        return null;
    }
}

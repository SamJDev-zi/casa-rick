package com.casarick.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleDTO {
    private Long id;
    private String description;
    private int quantity;
    private BigDecimal discount;
    private BigDecimal total;
    private LocalDateTime created;
    private LocalDateTime updated;
    private CustomerDTO customerDTO;
    private EmployeeDTO employeeDTO;
    private List<InventoryDTO> inventoryDTOList;
}

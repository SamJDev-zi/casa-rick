package com.casarick.api.dto;

import com.casarick.api.model.Inventory;
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
public class SaleRequestDTO {
    private Long id;
    private String description;
    private int quantity;
    private BigDecimal amount;
    private BigDecimal discount;
    private BigDecimal total;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Long customerId;
    private Long employeeId;
    private Long branchId;
    private List<Inventory> inventoryList;
}

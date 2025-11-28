package com.casarick.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryRequestDTO {
    private Long id;
    private Long productId;
    private int stock;
    private BigDecimal costPrice;
    private BigDecimal salePrice;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Long branchId;
}

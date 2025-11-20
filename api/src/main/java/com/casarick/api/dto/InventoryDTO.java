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
public class InventoryDTO {
    private Long id;
    private ProductDTO productDTO;
    private int stock;
    private BigDecimal costPrice;
    private BigDecimal salePrice;
    private LocalDateTime created;
    private LocalDateTime updated;
    private BranchDTO branchDTO;

    //campo calculado (stock * salePrice) - (stock * costPrice)
    private BigDecimal aproximateProfit;
}

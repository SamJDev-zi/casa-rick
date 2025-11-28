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
public class ReservationRequestDTO {
    private Long id;
    private String description;
    private BigDecimal amount;
    private BigDecimal balance;
    private String status;
    private int quantity;
    private LocalDateTime created;
    private LocalDateTime updated;
    private LocalDateTime expirationDate;
    private Long customerId;
    private Long employeeId;
    private Long branchId;
    private List<InventoryRequestDTO> inventoryRequestDTOList;
}

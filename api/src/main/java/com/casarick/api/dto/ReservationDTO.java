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
public class ReservationDTO {
    private Long id;
    private String description;
    private BigDecimal amount;
    private BigDecimal balance;
    private String status;
    private int quantity;
    private LocalDateTime created;
    private LocalDateTime expirationDate;
    private LocalDateTime updated;
    private CustomerDTO customerDTO;
    private EmployeeDTO employeeDTO;
    private BranchDTO branchDTO;
}

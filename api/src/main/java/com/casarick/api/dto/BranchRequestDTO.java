package com.casarick.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchRequestDTO {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private Long managerId;
    private boolean isActive;
}

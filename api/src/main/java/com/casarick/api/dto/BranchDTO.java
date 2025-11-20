package com.casarick.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchDTO {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private ManagerDTO manager;
    private boolean isActive;
}

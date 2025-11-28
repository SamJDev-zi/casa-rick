package com.casarick.api.dto;

import com.casarick.api.model.Manager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchResponseDTO {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private ManagerResponseDTO manager;
    private boolean isActive;
}

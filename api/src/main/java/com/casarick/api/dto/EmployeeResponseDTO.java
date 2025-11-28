package com.casarick.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResponseDTO {
    private Long id;
    private String name;
    private String lastName;
    private String phonNumber;
    private boolean isActive;
    private List<PermissionDTO> permissionDTOList;
    private BranchResponseDTO branch;
}

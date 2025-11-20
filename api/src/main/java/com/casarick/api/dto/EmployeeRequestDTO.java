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
public class EmployeeRequestDTO {
    private Long id;
    private String name;
    private String lastName;
    private String phonNumber;
    private String password;
    private boolean isActive;
    private List<PermissionDTO> permissionDTOList;
}

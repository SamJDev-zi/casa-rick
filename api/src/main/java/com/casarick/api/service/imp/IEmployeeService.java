package com.casarick.api.service.imp;

import com.casarick.api.dto.EmployeeResponseDTO;
import com.casarick.api.dto.EmployeeRequestDTO;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    List<EmployeeResponseDTO> getEmployees();
    EmployeeResponseDTO getEmployeeById(Long id);
    Optional<EmployeeResponseDTO> getEmployeeByName(String name);
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO);
    EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO requestDTO);
    void deleteEmployee(Long id);
}

package com.casarick.api.service.imp;

import com.casarick.api.dto.EmployeeDTO;
import com.casarick.api.dto.EmployeeRequestDTO;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    List<EmployeeDTO> getEmployees();
    EmployeeDTO getEmployeeById(Long id);
    Optional<EmployeeDTO> getEmployeeByName(String name);
    EmployeeDTO createEmployee(EmployeeRequestDTO requestDTO);
    EmployeeDTO updateEmployee(Long id, EmployeeRequestDTO requestDTO);
    void deleteEmployee(Long id);
}

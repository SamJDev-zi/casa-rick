package com.casarick.api.service;

import com.casarick.api.dto.EmployeeResponseDTO;
import com.casarick.api.dto.EmployeeRequestDTO;
import com.casarick.api.exception.NotFoundException;
import com.casarick.api.mapper.Mapper;
import com.casarick.api.model.Branch;
import com.casarick.api.model.Employee;
import com.casarick.api.reposiroty.BranchRepository;
import com.casarick.api.reposiroty.EmployeeRepository;
import com.casarick.api.service.imp.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Override
    public List<EmployeeResponseDTO> getEmployees() {
        return employeeRepository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + id));

        return Mapper.toDTO(employee);
    }

    @Override
    public Optional<EmployeeResponseDTO> getEmployeeByName(String name) {
        Optional<Employee> employeeOptional = employeeRepository.getEmployeeByName(name);

        return employeeOptional.map(Mapper::toDTO);
    }

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO) {
        Employee employee = new Employee();

        Branch branch = branchRepository.findById(requestDTO.getBranchId())
                .orElseThrow(() -> new NotFoundException("Branch not found with id: " + requestDTO.getBranchId()));

        employee.setId(requestDTO.getId());
        employee.setName(requestDTO.getName());
        employee.setLastName(requestDTO.getLastName());
        employee.setPhoneNumber(requestDTO.getPhonNumber());
        employee.setPassword(requestDTO.getPassword());
        employee.setActive(true);
        employee.setPermissionList(requestDTO.getPermissionList());
        employee.setBranch(branch);

        return Mapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO requestDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found by id: " + id));

        Branch branch = branchRepository.findById(requestDTO.getBranchId())
                .orElseThrow(() -> new NotFoundException("Branch not found with id: " + requestDTO.getBranchId()));

        employee.setName(requestDTO.getName());
        employee.setLastName(requestDTO.getLastName());
        employee.setPhoneNumber(requestDTO.getPhonNumber());
        employee.setPassword(requestDTO.getPassword());
        employee.setActive(requestDTO.isActive());
        employee.setPermissionList(requestDTO.getPermissionList());
        employee.setBranch(branch);

        return Mapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found"));
        employeeRepository.delete(employee);
    }
}

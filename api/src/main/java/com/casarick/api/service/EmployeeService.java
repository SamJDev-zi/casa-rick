package com.casarick.api.service;

import com.casarick.api.dto.EmployeeDTO;
import com.casarick.api.dto.EmployeeRequestDTO;
import com.casarick.api.dto.PermissionDTO;
import com.casarick.api.exception.NotFoundException;
import com.casarick.api.mapper.Mapper;
import com.casarick.api.model.Employee;
import com.casarick.api.model.Permission;
import com.casarick.api.reposiroty.EmployeeRepository;
import com.casarick.api.service.imp.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeDTO> getEmployees() {
        return employeeRepository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + id));

        return Mapper.toDTO(employee);
    }

    @Override
    public Optional<EmployeeDTO> getEmployeeByName(String name) {
        Optional<Employee> employeeOptional = employeeRepository.getEmployeeByName(name);

        return employeeOptional.map(Mapper::toDTO);
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeRequestDTO requestDTO) {
        Employee employee = new Employee();

        List<Permission> permissionList = new ArrayList<>();
        for (PermissionDTO p : requestDTO.getPermissionDTOList()) {
            permissionList.add(Mapper.toEntity(p));
        }

        employee.setId(requestDTO.getId());
        employee.setName(requestDTO.getName());
        employee.setLastName(requestDTO.getLastName());
        employee.setPhoneNumber(requestDTO.getPhonNumber());
        employee.setPassword(requestDTO.getPassword());
        employee.setActive(true);
        employee.setPermissionList(permissionList);
        employee.setBranch(requestDTO.getBranch());

        return Mapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeRequestDTO requestDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found by id: " + id));

        List<Permission> permissionList = new ArrayList<>();
        for (PermissionDTO p : requestDTO.getPermissionDTOList()) {
            permissionList.add(Mapper.toEntity(p));
        }

        employee.setName(requestDTO.getName());
        employee.setLastName(requestDTO.getLastName());
        employee.setPhoneNumber(requestDTO.getPhonNumber());
        employee.setPassword(requestDTO.getPassword());
        employee.setActive(requestDTO.isActive());
        employee.setPermissionList(permissionList);
        employee.setBranch(requestDTO.getBranch());
        return null;
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found"));
        employeeRepository.delete(employee);
    }
}

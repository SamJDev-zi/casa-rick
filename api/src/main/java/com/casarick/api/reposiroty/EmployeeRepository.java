package com.casarick.api.reposiroty;

import com.casarick.api.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> getEmployeeByName(String name);
}

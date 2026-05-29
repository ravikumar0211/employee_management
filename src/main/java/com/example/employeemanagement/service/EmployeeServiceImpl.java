package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.repository.EmployeeSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Page<EmployeeDTO> getAllEmployees(String department, String name, String email, Pageable pageable) {
        // Combine all incoming query predicates dynamically
        Specification<Employee> spec = Specification
                .where(EmployeeSpecifications.hasDepartment(department))
                .and(EmployeeSpecifications.hasName(name))
                .and(EmployeeSpecifications.hasEmail(email));

        // Database executes optimized query returning only requested page window chunk
        Page<Employee> employeePage = employeeRepository.findAll(spec, pageable);

        // Map page layout elements from Entity format to DTO layout
        return employeePage.map(this::mapToDTO);
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .salary(employee.getSalary())
                .build();
    }
}
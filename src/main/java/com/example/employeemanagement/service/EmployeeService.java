package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    // Other CRUD operations remain untouched
    Page<EmployeeDTO> getAllEmployees(String department, String name, String email, Pageable pageable);
}
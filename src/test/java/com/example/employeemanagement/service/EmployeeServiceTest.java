package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.exception.ResourceNotFoundException;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1L)
                .name("Alice")
                .email("alice@example.com")
                .department("Engineering")
                .salary(95000.0)
                .build();

        employeeDTO = EmployeeDTO.builder()
                .name("Alice")
                .email("alice@example.com")
                .department("Engineering")
                .salary(95000.0)
                .build();
    }

    @Test
    void givenEmployeeDTO_whenSaveEmployee_thenReturnSavedEmployeeDTO() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDTO savedDto = employeeService.createEmployee(employeeDTO);

        assertThat(savedDto).isNotNull();
        assertThat(savedDto.getId()).isEqualTo(1L);
        assertThat(savedDto.getName()).isEqualTo("Alice");
    }

    @Test
    void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeDTO() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        EmployeeDTO foundDto = employeeService.getEmployeeById(1L);

        assertThat(foundDto).isNotNull();
        assertThat(foundDto.getEmail()).isEqualTo("alice@example.com");
    }

    @Test
    void givenInvalidEmployeeId_whenGetEmployeeById_thenThrowException() {
        when(employeeRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(2L));
    }
}
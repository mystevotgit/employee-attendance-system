package net.guides.springboot2.crud.service;

import net.guides.springboot2.crud.dto.EmployeeDTO;
import net.guides.springboot2.crud.exception.ResourceNotFoundException;
import net.guides.springboot2.crud.model.Employee;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Set;

public interface EmployeeService {
    Set<EmployeeDTO> getAllFirmEmployees(Long firmId);

    ResponseEntity<EmployeeDTO> getEmployeeById(Long employeeId)
            throws ResourceNotFoundException;

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    ResponseEntity<EmployeeDTO> updateEmployee(Long employeeId, Employee employeeDetails)
            throws ResourceNotFoundException;

    Map<String, Boolean> deleteEmployee(Long employeeId)
            throws ResourceNotFoundException;
}

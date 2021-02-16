package net.guides.springboot2.crud.controller;

import net.guides.springboot2.crud.dto.EmployeeDTO;
import net.guides.springboot2.crud.exception.ResourceNotFoundException;
import net.guides.springboot2.crud.model.Employee;
import net.guides.springboot2.crud.model.User;
import net.guides.springboot2.crud.service.EmployeeService;
import net.guides.springboot2.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;


	@GetMapping("/employees/{firmId}")
	public Set<EmployeeDTO> getAllFirmEmployees(@PathVariable(value = "firmId") Long firmId) {
		return employeeService.getAllFirmEmployees(firmId);
	}

	@GetMapping("/employees/employee/{id}")
	public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		return employeeService.getEmployeeById(employeeId);
	}

	@PostMapping("/employees")
	public EmployeeDTO createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
		return employeeService.createEmployee(employeeDTO);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		return employeeService.updateEmployee(employeeId, employeeDetails);
	}

	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		return employeeService.deleteEmployee(employeeId);
	}
}

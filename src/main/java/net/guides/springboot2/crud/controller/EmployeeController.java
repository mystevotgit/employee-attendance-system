package net.guides.springboot2.crud.controller;

import net.guides.springboot2.crud.dto.EmployeeDTO;
import net.guides.springboot2.crud.exception.ResourceNotFoundException;
import net.guides.springboot2.crud.model.Employee;
import net.guides.springboot2.crud.model.User;
import net.guides.springboot2.crud.repository.EmployeeRepository;
import net.guides.springboot2.crud.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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
	private EmployeeRepository employeeRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;

	private void setModelMappingStrategy() {
		modelMapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.LOOSE);
	}

	@GetMapping("/employees/{firmId}")
	public Set<EmployeeDTO> getAllFirmEmployees(@PathVariable(value = "firmId") Long firmId) {
		Optional<User> firm = userRepository.findById(firmId);
		User firmData = firm.get();
		setModelMappingStrategy();
		return firmData.getEmployees().stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class))
				.collect(Collectors.toSet());
	}

	@GetMapping("/employees/employee/{id}")
	public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		setModelMappingStrategy();
		EmployeeDTO employeeDTO = modelMapper
				.map(employee, EmployeeDTO.class);
		return ResponseEntity.ok().body(employeeDTO);
	}

	@PostMapping("/employees")
	public EmployeeDTO createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
		setModelMappingStrategy();
		Employee employee = new Employee();
		employee.setEmailId(employeeDTO.getEmailId());
		employee.setFirstName(employeeDTO.getFirstName());
		employee.setLastName(employeeDTO.getLastName());
		employee.setFirm(userRepository.findById(employeeDTO.getFirmId()).get());
		Employee employee1 = employeeRepository.save(employee);
		EmployeeDTO employeeDTO1 = modelMapper.map(employee1, EmployeeDTO.class);
 		return employeeDTO1;
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		setModelMappingStrategy();
		employee.setEmailId(employeeDetails.getEmailId());
		employee.setLastName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getFirstName());
		final Employee updatedEmployee = employeeRepository.save(employee);
		EmployeeDTO employeeDTO = modelMapper.map(updatedEmployee, EmployeeDTO.class);
		return ResponseEntity.ok(employeeDTO);
	}

	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}

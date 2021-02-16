package net.guides.springboot2.crud.service;

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
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{
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


    @Override
    public Set<EmployeeDTO> getAllFirmEmployees(Long firmId) {
        Optional<User> firm = userRepository.findById(firmId);
        User firmData = firm.get();
        setModelMappingStrategy();
        return firmData.getEmployees().stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toSet());
    }

    @Override
    public ResponseEntity<EmployeeDTO> getEmployeeById(Long employeeId) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
        setModelMappingStrategy();
        EmployeeDTO employeeDTO = modelMapper
                .map(employee, EmployeeDTO.class);
        return ResponseEntity.ok().body(employeeDTO);
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
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

    @Override
    public ResponseEntity<EmployeeDTO> updateEmployee(Long employeeId, Employee employeeDetails) throws ResourceNotFoundException {
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

    @Override
    public Map<String, Boolean> deleteEmployee(Long employeeId) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

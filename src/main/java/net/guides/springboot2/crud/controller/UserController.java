package net.guides.springboot2.crud.controller;

import net.guides.springboot2.crud.dto.UserDTO;
import net.guides.springboot2.crud.exception.ResourceNotFoundException;
import net.guides.springboot2.crud.model.User;
import net.guides.springboot2.crud.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/firms")
    public List<UserDTO> getAllFirms() {
        List<User> users = userRepository.findAll();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        List<UserDTO> userDTOS = users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        return userDTOS;
    }

    @GetMapping("/firms/{id}")
    public ResponseEntity<UserDTO> getFirmById(@PathVariable(value = "id") Long firmId)
            throws ResourceNotFoundException {
        User firm = userRepository.findById(firmId)
                .orElseThrow(() -> new ResourceNotFoundException("Firm not found for this id :: " + firmId));
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        UserDTO userDTO = modelMapper
                .map(firm, UserDTO.class);
        return ResponseEntity.ok().body(userDTO);
    }

    @PostMapping("/firms")
    public UserDTO createFirm(@Valid @RequestBody User firm) {
        User user = userRepository.save(firm);
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        UserDTO returnObj = modelMapper
                .map(user, UserDTO.class);
        return returnObj;
    }

    @PutMapping("/firms/{id}")
    public ResponseEntity<UserDTO> updateFirm(@PathVariable(value = "id") Long firmId,
                                                   @Valid @RequestBody UserDTO firmDetails) throws ResourceNotFoundException {
        User firm = userRepository.findById(firmId)
                .orElseThrow(() -> new ResourceNotFoundException("Firm not found for this id :: " + firmId));
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        firm.setEmailId(firmDetails.getEmailId());
        firm.setName(firmDetails.getName());
        final User updatedFirm = userRepository.save(firm);
        UserDTO returnObj = modelMapper
                .map(updatedFirm, UserDTO.class);
        return ResponseEntity.ok(returnObj);
    }

    @DeleteMapping("/firms/{id}")
    public Map<String, Boolean> deleteFirm(@PathVariable(value = "id") Long firmId)
            throws ResourceNotFoundException {
        User firm = userRepository.findById(firmId)
                .orElseThrow(() -> new ResourceNotFoundException("Firm not found for this id :: " + firmId));
        userRepository.delete(firm);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

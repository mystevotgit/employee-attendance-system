package net.guides.springboot2.crud.controller;

import net.guides.springboot2.crud.dto.UserDTO;
import net.guides.springboot2.crud.exception.ResourceNotFoundException;
import net.guides.springboot2.crud.model.User;
import net.guides.springboot2.crud.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    private void setModelMappingStrategy() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    @GetMapping("/firms")
    public List<UserDTO> getAllFirms() {
        return userService.getAllFirms();
    }

    @GetMapping("/firms/{id}")
    public ResponseEntity<UserDTO> getFirmById(@PathVariable(value = "id") Long firmId)
            throws ResourceNotFoundException {
        return userService.getFirmById(firmId);
    }

    @PostMapping("/firms")
    public UserDTO createFirm(@Valid @RequestBody User firm) {
        return userService.createFirm(firm);
    }

    @PutMapping("/firms/{id}")
    public ResponseEntity<UserDTO> updateFirm(@PathVariable(value = "id") Long firmId,
                                                   @Valid @RequestBody UserDTO firmDetails) throws ResourceNotFoundException {
        return userService.updateFirm(firmId, firmDetails);
    }

    @DeleteMapping("/firms/{id}")
    public Map<String, Boolean> deleteFirm(@PathVariable(value = "id") Long firmId) throws ResourceNotFoundException {
        return userService.deleteFirm(firmId);
    }
}

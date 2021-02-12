package net.guides.springboot2.crud.controller;

import net.guides.springboot2.crud.exception.ResourceNotFoundException;
import net.guides.springboot2.crud.model.Employee;
import net.guides.springboot2.crud.model.User;
import net.guides.springboot2.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/firms")
    public List<User> getAllFirms() {
        return userRepository.findAll();
    }

    @GetMapping("/firms/{id}")
    public ResponseEntity<User> getFirmById(@PathVariable(value = "id") Long firmId)
            throws ResourceNotFoundException {
        User firm = userRepository.findById(firmId)
                .orElseThrow(() -> new ResourceNotFoundException("Firm not found for this id :: " + firmId));
        return ResponseEntity.ok().body(firm);
    }

    @PostMapping("/firms")
    public User createFirm(@Valid @RequestBody User firm) {
        return userRepository.save(firm);
    }

    @PutMapping("/firms/{id}")
    public ResponseEntity<User> updateFirm(@PathVariable(value = "id") Long firmId,
                                                   @Valid @RequestBody User firmDetails) throws ResourceNotFoundException {
        User firm = userRepository.findById(firmId)
                .orElseThrow(() -> new ResourceNotFoundException("Firm not found for this id :: " + firmId));

        firm.setEmailId(firmDetails.getEmailId());
        firm. setName(firmDetails.getName());
        firm.setEmailId(firmDetails.getEmailId());
        final User updatedFirm = userRepository.save(firm);
        return ResponseEntity.ok(updatedFirm);
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

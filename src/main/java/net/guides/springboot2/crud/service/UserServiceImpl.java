package net.guides.springboot2.crud.service;

import net.guides.springboot2.crud.dto.UserDTO;
import net.guides.springboot2.crud.exception.ResourceNotFoundException;
import net.guides.springboot2.crud.model.User;
import net.guides.springboot2.crud.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private void setModelMappingStrategy() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
    }
    @Override
    public List<UserDTO> getAllFirms() {
        List<User> users = userRepository.findAll();
        setModelMappingStrategy();
        List<UserDTO> userDTOS = users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        return userDTOS;
    }

    @Override
    public UserDTO createFirm(User firm) {
        User user = userRepository.save(firm);
        setModelMappingStrategy();
        UserDTO returnObj = modelMapper
                .map(user, UserDTO.class);
        return returnObj;
    }

    @Override
    public ResponseEntity<UserDTO> getFirmById(long firmId) throws ResourceNotFoundException {
        User firm = userRepository.findById(firmId)
                .orElseThrow(() -> new ResourceNotFoundException("Firm not found for this id :: " + firmId));
        setModelMappingStrategy();
        UserDTO userDTO = modelMapper
                .map(firm, UserDTO.class);
        return ResponseEntity.ok().body(userDTO);
    }

    @Override
    public ResponseEntity<UserDTO> updateFirm(Long firmId, UserDTO firmDetails) throws ResourceNotFoundException {
            User firm = userRepository.findById(firmId)
                    .orElseThrow(() -> new ResourceNotFoundException("Firm not found for this id :: " + firmId));
            setModelMappingStrategy();
            firm.setEmailId(firmDetails.getEmailId());
            firm.setName(firmDetails.getName());
            final User updatedFirm = userRepository.save(firm);
            UserDTO returnObj = modelMapper
                    .map(updatedFirm, UserDTO.class);
            return ResponseEntity.ok(returnObj);
    }

    @Override
    public Map<String, Boolean> deleteFirm(Long firmId) throws ResourceNotFoundException {
        User firm = userRepository.findById(firmId)
                .orElseThrow(() -> new ResourceNotFoundException("Firm not found for this id :: " + firmId));
        userRepository.delete(firm);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

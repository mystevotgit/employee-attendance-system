package net.guides.springboot2.crud.service;

import net.guides.springboot2.crud.dto.UserDTO;
import net.guides.springboot2.crud.exception.ResourceNotFoundException;
import net.guides.springboot2.crud.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<UserDTO>  getAllFirms();

    UserDTO createFirm(User firm);

    ResponseEntity<UserDTO> getFirmById(long firmId) throws ResourceNotFoundException;

    ResponseEntity<UserDTO> updateFirm(Long firmId, UserDTO firmDetails)throws ResourceNotFoundException;

    Map<String, Boolean> deleteFirm(Long firmId) throws ResourceNotFoundException;
}

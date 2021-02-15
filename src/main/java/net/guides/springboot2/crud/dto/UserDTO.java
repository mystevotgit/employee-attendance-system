package net.guides.springboot2.crud.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.guides.springboot2.crud.model.Employee;
import net.guides.springboot2.crud.model.Meeting;

import java.time.LocalDateTime;
import java.util.Set;

@Data @NoArgsConstructor
public class UserDTO {
    private long id;
    private String name;
    private String emailId;
    private Set<MeetingDTO> meetings;
    private Set<EmployeeDTO> employees;
    private LocalDateTime timeStamp;
    private LocalDateTime created;
}

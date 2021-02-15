package net.guides.springboot2.crud.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.guides.springboot2.crud.model.Attendance;
import net.guides.springboot2.crud.model.Employee;
import net.guides.springboot2.crud.model.Meeting;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data @NoArgsConstructor
public class EmployeeDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private long firmId;
    private Set<Attendance> meetings = new HashSet<>();
    private LocalDateTime timeStamp;
    private LocalDateTime created;
}

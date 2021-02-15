package net.guides.springboot2.crud.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor
public class AttendanceDTO {
    private long id;
    private long meetingId;
    private long employeeId;
    private boolean attendance;
    private LocalDateTime timeStamp;
    private LocalDateTime created;
}

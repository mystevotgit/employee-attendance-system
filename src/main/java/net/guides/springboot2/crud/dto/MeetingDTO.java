package net.guides.springboot2.crud.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.guides.springboot2.crud.model.Attendance;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
public class MeetingDTO {
    private long id;
    private String title;
    private String agenda;
    private long firmId;
    private Set<Attendance> attendance;
    private String meetingTime;
    private LocalDateTime timeStamp;
    private LocalDateTime created;
}

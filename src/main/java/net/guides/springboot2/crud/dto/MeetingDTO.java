package net.guides.springboot2.crud.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MeetingDTO {
    private long id;
    private String title;
    private String agenda;
    private long firmId;
    private String meetingTime;
    private LocalDateTime timeStamp;
    private LocalDateTime created;
}

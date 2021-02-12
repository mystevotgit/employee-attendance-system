package net.guides.springboot2.crud.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "meetings")
@Data
public class Meeting {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id
    @Setter(AccessLevel.PROTECTED) long id;
    @Column(name = "meeting_title", nullable = false)
    private String title;
    @Column(name = "meeting_agenda", nullable = false)
    private String agenda;

    @ManyToOne
    @Column(name = "firm_id", nullable = false)
    private User firm;
    @Column(name = "meeting_time", nullable = false)
    private LocalDateTime meetingTime;
    @UpdateTimestamp
    @Column(name = "timestamp", nullable = false)
    private @Setter(AccessLevel.PROTECTED) LocalDateTime timeStamp;
    @CreationTimestamp
    @Column(name = "created", nullable = false)
    private @Setter(AccessLevel.PROTECTED) LocalDateTime created;

    public Meeting(String title, String agenda, User firm, LocalDateTime meetingTime) {
        this.title = title;
        this.agenda = agenda;
        this.firm = firm;
        this.meetingTime = meetingTime;
    }
}
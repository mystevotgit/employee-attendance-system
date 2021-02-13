package net.guides.springboot2.crud.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "meetings")
@Data @NoArgsConstructor
public class Meeting {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id
    @Setter(AccessLevel.PROTECTED) long id;
    @Column(name = "meeting_title", nullable = false)
    private String title;
    @Column(name = "meeting_agenda", nullable = false)
    private String agenda;

    @ManyToOne
    private User firm;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Attendance> attendance;

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

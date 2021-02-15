package net.guides.springboot2.crud.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance")
@Getter
@Setter
@NoArgsConstructor
public class Attendance {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id
    @Setter(AccessLevel.PROTECTED) long id;

    @ManyToOne
    private Meeting meeting;

    @ManyToOne
    private Employee employee;

    @Column(name = "attendance", nullable = false)
    private boolean attendance;
    @UpdateTimestamp
    @Column(name = "timestamp", nullable = false)
    private @Setter(AccessLevel.PROTECTED)
    LocalDateTime timeStamp;
    @CreationTimestamp
    @Column(name = "created", nullable = false)
    private @Setter(AccessLevel.PROTECTED) LocalDateTime created;

    public Attendance(Meeting meeting, Employee employee, boolean attendance) {
        this.meeting = meeting;
        this.employee = employee;
        this.attendance = attendance;
    }
}

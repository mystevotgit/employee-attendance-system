package net.guides.springboot2.crud.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance")
@Data
public class Attendance {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id
    @Setter(AccessLevel.PROTECTED) long id;
    @ManyToOne
    @Column(name = "meeting_id", nullable = false)
    private Meeting meeting;
    @ManyToOne
    @Column(name = "employee_id", nullable = false)
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
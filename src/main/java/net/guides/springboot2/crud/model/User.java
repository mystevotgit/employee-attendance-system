package net.guides.springboot2.crud.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "firms")
@Data
public class User {
    private @Id
    @Setter(AccessLevel.PROTECTED) long id;
    @Column(name = "firm_name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    private String emailId;

    @UpdateTimestamp
    private @Setter(AccessLevel.PROTECTED) LocalDateTime timeStamp;
    @CreationTimestamp
    private @Setter(AccessLevel.PROTECTED) LocalDateTime created;

    public User(String name, String emailId) {
        this.name = name;
        this.emailId = emailId;
    }
}

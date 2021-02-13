package net.guides.springboot2.crud.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "firms")
@Data @NoArgsConstructor
public class User {
    private @Id
    @Setter(AccessLevel.PROTECTED) long id;
    @Column(name = "firm_name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    private String emailId;

    @OneToMany(mappedBy = "firm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Meeting> meetings;

    @OneToMany(mappedBy = "firm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Employee> employees;

    @UpdateTimestamp
    @Column(name = "timestamp", nullable = false)
    private @Setter(AccessLevel.PROTECTED)
    LocalDateTime timeStamp;
    @CreationTimestamp
    @Column(name = "created", nullable = false)
    private @Setter(AccessLevel.PROTECTED) LocalDateTime created;

    public User(String name, String emailId) {
        this.name = name;
        this.emailId = emailId;
    }
}

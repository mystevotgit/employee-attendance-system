package net.guides.springboot2.crud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
public class Employee {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private @Id
	@Setter(AccessLevel.PROTECTED) long id;
	@Column(name = "first_name", nullable = false)
	private String firstName;
	@Column(name = "last_name", nullable = false)
	private String lastName;
	@Column(name = "email_address", nullable = false)
	private String emailId;

	@ManyToOne
	@JsonIgnore
	private User firm;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Attendance> meetings;

	@UpdateTimestamp
	@Column(name = "timestamp", nullable = false)
	private @Setter(AccessLevel.PROTECTED)
	LocalDateTime timeStamp;
	@CreationTimestamp
	@Column(name = "created", nullable = false)
	private @Setter(AccessLevel.PROTECTED) LocalDateTime created;
	
	public Employee(String firstName, String lastName, String emailId, User firm) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.firm = firm;
	}
}

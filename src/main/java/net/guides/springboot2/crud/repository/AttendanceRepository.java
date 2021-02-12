package net.guides.springboot2.crud.repository;

import net.guides.springboot2.crud.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

}

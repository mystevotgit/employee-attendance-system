package net.guides.springboot2.crud.repository;

import net.guides.springboot2.crud.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

}

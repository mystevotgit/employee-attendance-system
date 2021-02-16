package net.guides.springboot2.crud.service;

import net.guides.springboot2.crud.dto.AttendanceDTO;
import net.guides.springboot2.crud.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Map;

public interface AttendanceService {
    AttendanceDTO addToMeeting(AttendanceDTO attendanceDTO);

    ResponseEntity<AttendanceDTO> updateAttendance(Long attendanceId, AttendanceDTO attendanceDetails)
            throws ResourceNotFoundException;

    Map<String, Boolean> removeFromMeeting(Long attendanceId)
            throws ResourceNotFoundException;
}

package net.guides.springboot2.crud.controller;

import net.guides.springboot2.crud.dto.AttendanceDTO;
import net.guides.springboot2.crud.exception.ResourceNotFoundException;
import net.guides.springboot2.crud.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/attendance")
    public AttendanceDTO addToMeeting(@Valid @RequestBody AttendanceDTO attendanceDTO) {
        return attendanceService.addToMeeting(attendanceDTO);
    }

    @PutMapping("/attendance/{id}")
    public ResponseEntity<AttendanceDTO> updateAttendance(@PathVariable(value = "id") Long attendanceId,
                                                    @Valid @RequestBody AttendanceDTO attendanceDetails) throws ResourceNotFoundException {
        return attendanceService.updateAttendance(attendanceId, attendanceDetails);
    }

    @DeleteMapping("/attendance/{id}")
    public Map<String, Boolean> removeFromMeeting(@PathVariable(value = "id") Long attendanceId)
            throws ResourceNotFoundException {
        return attendanceService.removeFromMeeting(attendanceId);
    }
}

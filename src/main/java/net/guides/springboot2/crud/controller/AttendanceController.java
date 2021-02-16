package net.guides.springboot2.crud.controller;

import net.guides.springboot2.crud.dto.AttendanceDTO;
import net.guides.springboot2.crud.exception.ResourceNotFoundException;
import net.guides.springboot2.crud.model.Attendance;
import net.guides.springboot2.crud.repository.AttendanceRepository;
import net.guides.springboot2.crud.repository.EmployeeRepository;
import net.guides.springboot2.crud.repository.MeetingRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AttendanceController {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    private void setModelMappingStrategy() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    @PostMapping("/attendance")
    public AttendanceDTO addToMeeting(@Valid @RequestBody AttendanceDTO attendanceDTO) {
        setModelMappingStrategy();
        Attendance attendance = new Attendance();
        attendance.setMeeting(meetingRepository.findById(attendanceDTO.getMeetingId()).get());
        attendance.setEmployee(employeeRepository.findById(attendanceDTO.getEmployeeId()).get());
        attendance.setAttendance(false);
        Attendance attendance1 = attendanceRepository.save(attendance);
        AttendanceDTO attendanceDTO1 = modelMapper.map(attendance1, AttendanceDTO.class);
        return attendanceDTO1;
    }

    @PutMapping("/attendance/{id}")
    public ResponseEntity<AttendanceDTO> updateAttendance(@PathVariable(value = "id") Long attendanceId,
                                                    @Valid @RequestBody AttendanceDTO attendanceDetails) throws ResourceNotFoundException {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found for this id :: " + attendanceId));
        setModelMappingStrategy();
        attendance.setAttendance(attendanceDetails.isAttendance());
        final Attendance updatedAttendance = attendanceRepository.save(attendance);
        AttendanceDTO attendanceDTO = modelMapper.map(updatedAttendance, AttendanceDTO.class);
        return ResponseEntity.ok(attendanceDTO);
    }

    @DeleteMapping("/attendance/{id}")
    public Map<String, Boolean> removeFromMeeting(@PathVariable(value = "id") Long attendanceId)
            throws ResourceNotFoundException {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found for this id :: " + attendanceId));
        attendanceRepository.delete(attendance);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

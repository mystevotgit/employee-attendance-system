package net.guides.springboot2.crud.service;

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
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AttendanceServiceImpl implements AttendanceService{
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


    @Override
    public AttendanceDTO addToMeeting(AttendanceDTO attendanceDTO) {
        setModelMappingStrategy();
        Attendance attendance = new Attendance();
        attendance.setMeeting(meetingRepository.findById(attendanceDTO.getMeetingId()).get());
        attendance.setEmployee(employeeRepository.findById(attendanceDTO.getEmployeeId()).get());
        attendance.setAttendance(false);
        Attendance attendance1 = attendanceRepository.save(attendance);
        AttendanceDTO attendanceDTO1 = modelMapper.map(attendance1, AttendanceDTO.class);
        return attendanceDTO1;
    }

    @Override
    public ResponseEntity<AttendanceDTO> updateAttendance(Long attendanceId, AttendanceDTO attendanceDetails) throws ResourceNotFoundException {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found for this id :: " + attendanceId));
        setModelMappingStrategy();
        attendance.setAttendance(attendanceDetails.isAttendance());
        final Attendance updatedAttendance = attendanceRepository.save(attendance);
        AttendanceDTO attendanceDTO = modelMapper.map(updatedAttendance, AttendanceDTO.class);
        return ResponseEntity.ok(attendanceDTO);
    }

    @Override
    public Map<String, Boolean> removeFromMeeting(Long attendanceId) throws ResourceNotFoundException {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found for this id :: " + attendanceId));
        attendanceRepository.delete(attendance);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

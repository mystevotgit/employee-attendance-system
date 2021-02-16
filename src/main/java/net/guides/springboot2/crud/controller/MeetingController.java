package net.guides.springboot2.crud.controller;

import net.guides.springboot2.crud.dto.EmployeeDTO;
import net.guides.springboot2.crud.dto.MeetingDTO;
import net.guides.springboot2.crud.exception.ResourceNotFoundException;
import net.guides.springboot2.crud.model.Employee;
import net.guides.springboot2.crud.model.Meeting;
import net.guides.springboot2.crud.model.User;
import net.guides.springboot2.crud.repository.EmployeeRepository;
import net.guides.springboot2.crud.repository.MeetingRepository;
import net.guides.springboot2.crud.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class MeetingController {
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    private void setModelMappingStrategy() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    @GetMapping("/meetings/{firmId}")
    public Set<MeetingDTO> getAllFirmMeetings(@PathVariable(value = "firmId") Long firmId) {
        Optional<User> firm = userRepository.findById(firmId);
        User firmData = firm.get();
        setModelMappingStrategy();
        return firmData.getMeetings().stream().map(meeting -> modelMapper.map(meeting, MeetingDTO.class))
                .collect(Collectors.toSet());
    }

    @GetMapping("/meetings/meeting/{id}")
    public ResponseEntity<MeetingDTO> getMeetingById(@PathVariable(value = "id") Long meetingId)
            throws ResourceNotFoundException {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found for this id :: " + meetingId));
        setModelMappingStrategy();
        MeetingDTO meetingDTO = modelMapper
                .map(meeting, MeetingDTO.class);
        return ResponseEntity.ok().body(meetingDTO);
    }

    @PostMapping("/meetings")
    public MeetingDTO createMeeting(@Valid @RequestBody MeetingDTO meetingDTO) {
        setModelMappingStrategy();
        Meeting meeting = new Meeting();
        meeting.setTitle(meetingDTO.getTitle());
        meeting.setAgenda(meetingDTO.getAgenda());
        meeting.setMeetingTime(meetingDTO.getMeetingTime());
        meeting.setFirm(userRepository.findById(meetingDTO.getFirmId()).get());
        Meeting meeting1 = meetingRepository.save(meeting);
        MeetingDTO meetingDTO1 = modelMapper.map(meeting1, MeetingDTO.class);
        return meetingDTO1;
    }

    @PutMapping("/meetings/{id}")
    public ResponseEntity<MeetingDTO> updateMeeting(@PathVariable(value = "id") Long meetingId,
                                                      @Valid @RequestBody MeetingDTO meetingDetails) throws ResourceNotFoundException {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found for this id :: " + meetingId));
        setModelMappingStrategy();
        meeting.setTitle(meetingDetails.getTitle());
        meeting.setAgenda(meetingDetails.getAgenda());
        meeting.setMeetingTime(meetingDetails.getMeetingTime());
        final Meeting updatedMeeting = meetingRepository.save(meeting);
        MeetingDTO meetingDTO = modelMapper.map(updatedMeeting, MeetingDTO.class);
        return ResponseEntity.ok(meetingDTO);
    }

    @DeleteMapping("/meetings/{id}")
    public Map<String, Boolean> deleteMeeting(@PathVariable(value = "id") Long meetingId)
            throws ResourceNotFoundException {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found for this id :: " + meetingId));

        meetingRepository.delete(meeting);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

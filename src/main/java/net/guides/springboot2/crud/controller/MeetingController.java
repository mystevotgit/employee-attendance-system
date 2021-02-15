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

    @GetMapping("/meetings/{firmId}")
    public Set<MeetingDTO> getAllFirmMeetings(@PathVariable(value = "firmId") Long firmId) {
        Optional<User> firm = userRepository.findById(firmId);
        User firmData = firm.get();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return firmData.getMeetings().stream().map(meeting -> modelMapper.map(meeting, MeetingDTO.class))
                .collect(Collectors.toSet());
    }

    @GetMapping("/meetings/meeting/{id}")
    public ResponseEntity<MeetingDTO> getMeetingById(@PathVariable(value = "id") Long meetingId)
            throws ResourceNotFoundException {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + meetingId));
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        MeetingDTO meetingDTO = modelMapper
                .map(meeting, MeetingDTO.class);
        return ResponseEntity.ok().body(meetingDTO);
    }

    @PostMapping("/meetings")
    public MeetingDTO createMeeting(@Valid @RequestBody MeetingDTO meetingDTO) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Meeting meeting = new Meeting();
        meeting.setTitle(meetingDTO.getTitle());
        meeting.setAgenda(meetingDTO.getAgenda());
        meeting.setMeetingTime(meetingDTO.getMeetingTime());
        meeting.setFirm(userRepository.findById(meetingDTO.getFirmId()).get());
        Meeting meeting1 = meetingRepository.save(meeting);
        MeetingDTO meetingDTO1 = modelMapper.map(meeting1, MeetingDTO.class);
        return meetingDTO1;
    }
}

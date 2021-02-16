package net.guides.springboot2.crud.controller;

import net.guides.springboot2.crud.dto.MeetingDTO;
import net.guides.springboot2.crud.exception.ResourceNotFoundException;
import net.guides.springboot2.crud.model.Meeting;
import net.guides.springboot2.crud.model.User;
import net.guides.springboot2.crud.service.MeetingService;
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
    private MeetingService meetingService;

    @GetMapping("/meetings/{firmId}")
    public Set<MeetingDTO> getAllFirmMeetings(@PathVariable(value = "firmId") Long firmId) {
        return meetingService.getAllFirmMeetings(firmId);
    }

    @GetMapping("/meetings/meeting/{id}")
    public ResponseEntity<MeetingDTO> getMeetingById(@PathVariable(value = "id") Long meetingId)
            throws ResourceNotFoundException {
        return meetingService.getMeetingById(meetingId);
    }

    @PostMapping("/meetings")
    public MeetingDTO createMeeting(@Valid @RequestBody MeetingDTO meetingDTO) {
        return meetingService.createMeeting(meetingDTO);
    }

    @PutMapping("/meetings/{id}")
    public ResponseEntity<MeetingDTO> updateMeeting(@PathVariable(value = "id") Long meetingId,
                                                      @Valid @RequestBody MeetingDTO meetingDetails) throws ResourceNotFoundException {
        return meetingService.updateMeeting(meetingId, meetingDetails);
    }

    @DeleteMapping("/meetings/{id}")
    public Map<String, Boolean> deleteMeeting(@PathVariable(value = "id") Long meetingId)
            throws ResourceNotFoundException {
        return meetingService.deleteMeeting(meetingId);
    }
}

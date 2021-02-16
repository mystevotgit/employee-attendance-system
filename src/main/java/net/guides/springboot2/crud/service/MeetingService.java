package net.guides.springboot2.crud.service;

import net.guides.springboot2.crud.dto.MeetingDTO;
import net.guides.springboot2.crud.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

public interface MeetingService {
    Set<MeetingDTO> getAllFirmMeetings(Long firmId);

    ResponseEntity<MeetingDTO> getMeetingById(Long meetingId)
            throws ResourceNotFoundException;

    MeetingDTO createMeeting(MeetingDTO meetingDTO);

    ResponseEntity<MeetingDTO> updateMeeting(Long meetingId, MeetingDTO meetingDetails)
            throws ResourceNotFoundException;

    Map<String, Boolean> deleteMeeting(Long meetingId)
            throws ResourceNotFoundException;
}

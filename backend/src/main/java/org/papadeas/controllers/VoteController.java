package org.papadeas.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.papadeas.dto.VoteDto;
import org.papadeas.services.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("api/vote")
public class VoteController {

    private final VoteService voteService;

    /**
     * Votes movie
     *
     * @param voteDto given dto with movie and user ids among with the preference
     * @return the updated VoteDto
     */
    @PostMapping
    public ResponseEntity<?> voteMovie(@RequestBody VoteDto voteDto) {
        return ResponseEntity.ok(voteService.voteMovie(voteDto));
    }
}

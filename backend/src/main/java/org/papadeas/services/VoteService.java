package org.papadeas.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.papadeas.dto.VoteDto;
import org.papadeas.mappers.VoteMapper;
import org.papadeas.model.Movie;
import org.papadeas.model.Vote;
import org.papadeas.repositories.VoteRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class VoteService extends BaseService<Vote, VoteDto> {


    private final VoteRepository voteRepository;

    private final VoteMapper voteMapper;

    private final MovieService movieService;

    @PostConstruct
    public void onInit() {
        setMapper(voteMapper);
        setRepository(voteRepository);
    }

    /**
     * Registers a vote from a user
     * @param voteDto the vote information
     * @return the updated vote info
     */
    public VoteDto voteMovie(VoteDto voteDto) {

        Movie movie = movieService.findResource(voteDto.getMovieId());
        validateVote(voteDto, movie);

        // if vote already exists withdraw or Change it
        Vote oldVote = voteRepository.findByUserIdAndMovieId(voteDto.getUserId(), voteDto.getMovieId());
        if (Objects.nonNull(oldVote)) {
            log.debug("Same vote detected");
            if (oldVote.getVote().equals(voteDto.getVote())) {
                //withdraw
                delete(oldVote.getId());
                return null;
            } else {
                //update
                voteDto.setId(oldVote.getId());
                return update(voteDto);
            }
        }
        log.info("Vote added");
        return create(voteDto);
    }

    /**
     * validates if the user is able to vote a specific movie
     * @param voteDto submitted vote
     * @param movie mote to be rated
     */
    private void validateVote(VoteDto voteDto, Movie movie) {
        if (movie.getCreatedBy().getId().equals(voteDto.getUserId())) {
            throw new IllegalStateException("Users shall not grade own movies");
        }
    }
}

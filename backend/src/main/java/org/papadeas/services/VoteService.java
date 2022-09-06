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

    public String voteMovie(VoteDto voteDto) {

        Movie movie = movieService.findResource(voteDto.getMovieId());
        //validation
        if (movie.getCreatedBy().getId().equals(voteDto.getUserId())) {
            throw new IllegalStateException("User shall not grade own movies");
        }

        // if vote already exists withdraw it or Change it
        Vote oldVote = voteRepository.findByUserIdAndMovieId(voteDto.getUserId(), voteDto.getMovieId());
        if (Objects.nonNull(oldVote)) {
            log.warn("Same vote");
            if (oldVote.getVote().equals(voteDto.getVote())) {
                //withdraw
                delete(oldVote.getId());
            } else {
                //update
                voteDto.setId(oldVote.getId());
                update(voteDto);
                return voteDto.getId();
            }
        }
        VoteDto vote = create(voteDto);
        return vote.getId();
    }
}

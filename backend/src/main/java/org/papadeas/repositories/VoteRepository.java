package org.papadeas.repositories;

import org.papadeas.model.Vote;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends GenericRepository<Vote> {


    /**
     * Finds a user's vote for a movie
     * @param userId user's id
     * @param movieId movie's id
     * @return the vote
     */
    Vote findByUserIdAndMovieId(String userId, String movieId);

}



package org.papadeas.repositories;

import org.papadeas.model.Movie;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepository extends GenericRepository<Movie>{

    /**
     * checks if a movie title exits
     *
     * @param title the movie title
     * @return true or false
     */
    boolean existsByTitle(String title);
}

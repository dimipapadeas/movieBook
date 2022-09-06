package org.papadeas.services;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.papadeas.dto.MovieDto;
import org.papadeas.mappers.MovieMapper;
import org.papadeas.model.Movie;
import org.papadeas.model.QMovie;
import org.papadeas.repositories.MoviesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieService extends BaseService<Movie, MovieDto> {


    private final MoviesRepository moviesRepository;

    private final MovieMapper mapper;

    private static final QMovie Q_MOVIE = QMovie.movie;


    @PostConstruct
    public void onInit() {
        setMapper(mapper);
        setRepository(moviesRepository);
    }


    @Override
    public MovieDto findById(String id) {
        return super.findById(id);
    }

    public Page<MovieDto> getAllMovies(int page, int size, String direction, String property, String filter) {
        Predicate predicate = null;
        if (Objects.nonNull(filter) && !filter.isEmpty()) {
            predicate = Q_MOVIE.title.eq(filter);
        }

        if (property.equals("likes")) {
            return getMovieDtoByLikes(page, size, direction, Comparator.comparing(MovieDto::getLikes));
        }
        if (property.equals("dislikes")) {
            return getMovieDtoByLikes(page, size, direction, Comparator.comparing(MovieDto::getDislikes));
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), property));
        return moviesRepository.findAll(pageable).map(getMapper()::mapToDTO);
    }

    private Page<MovieDto> getMovieDtoByLikes(int page, int size, String direction, Comparator<MovieDto> comparing) {
        List<MovieDto> movies = moviesRepository.findAll().stream().map(getMapper()::mapToDTO).collect(Collectors.toList());
        if (direction.equals("desc")) { // desc
            movies = movies.stream().sorted(comparing.reversed()).collect(Collectors.toList());
        } else { // asc
            movies = movies.stream().sorted(comparing).collect(Collectors.toList());
        }
        return getPage(page, size, movies);
    }


    //TODO
    public Page<MovieDto> getUsersMovies(int page, int size, String direction, String property, String userId) {
        Predicate predicate = null;
        if (Objects.nonNull(userId)) {
            predicate = Q_MOVIE.createdBy.id.eq(userId);
        }
//        if (Objects.nonNull(predicate)) {
//            return findPaginated(page, size, direction, property, predicate);
//        } else {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), property));
        return getRepository().findAll(pageable).map(getMapper()::mapToDTO);
    }
}

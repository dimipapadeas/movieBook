package org.papadeas.services;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.papadeas.dto.MovieDto;
import org.papadeas.exception.AppGenericException;
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

  /**
   * Creates a movie entry
   *
   * @param dto the DTO to be mapped to the created entity
   * @return the created movie
   * @throws AppGenericException in case the movie title already exists
   */
  @Override
  public MovieDto create(MovieDto dto) throws AppGenericException {
    validateMovieName(dto);
    return super.create(dto);
  }

  /**
   * checks during a new registration if a username is already taken
   *
   * @param dto the submitted new movie
   * @throws AppGenericException that the movie already exists.
   */
  private void validateMovieName(MovieDto dto) throws AppGenericException {
    if (Objects.isNull(dto.getId())) {
      if (moviesRepository.existsByTitle(dto.getTitle())) {
        throw new AppGenericException("Movie title already exists");
      }
    }
  }

  /**
   * Retrieves the move for the given id.
   *
   * @param id the movie id
   * @return the moveDto.
   */
  @Override
  public MovieDto findById(String id) {
    return super.findById(id);
  }


  /**
   * Retrieves all movies
   *
   * @param page current page
   * @param size page size
   * @param direction ascending or descending
   * @param property field to sort on
   * @param filter keyword to search with
   * @param username username to enable user's movies search
   * @return the pageable results
   */
  public Page<MovieDto> getAllMovies(int page, int size, String direction, String property,
      String filter, String username) {
    Predicate predicate = null;
    if (Objects.nonNull(filter) && !filter.isEmpty()) {
      predicate = Q_MOVIE.title.like("%" + filter + "%");
    }
    if (Objects.nonNull(username) && !username.isEmpty()) {
      predicate = Q_MOVIE.createdBy.username.eq(username);
    }
    return findMovies(page, size, direction, property, predicate);
  }


  /**
   * @param page current page
   * @param size page size
   * @param direction ascending or descending
   * @param property field to sort on
   * @param predicate for cases of search with keyword
   * @return pageable result
   */
  private Page<MovieDto> findMovies(int page, int size, String direction, String property,
      Predicate predicate) {

    Pageable pageable = PageRequest.of(page, size,
        Sort.by(Sort.Direction.fromString(direction), property));

    if (property.equals("likes")) {
      return getMovieDtoOrderedByLikes(predicate, page, size, direction,
          Comparator.comparing(MovieDto::getLikes));
    }
    if (property.equals("dislikes")) {
      return getMovieDtoOrderedByLikes(predicate, page, size, direction,
          Comparator.comparing(MovieDto::getDislikes));
    }
    if (Objects.nonNull(predicate)) {
      return moviesRepository.findAll(predicate, pageable).map(getMapper()::mapToDTO);
    }
    return moviesRepository.findAll(pageable).map(getMapper()::mapToDTO);
  }


  /**
   * In case of like sorting we have to sort in app level.
   *
   * @param predicate for cases of search with keyword
   * @param page current page
   * @param size page size
   * @param direction ascending or descending
   * @param comparing compare item, likes or dislikes.
   * @return the pageable result.
   */
  private Page<MovieDto> getMovieDtoOrderedByLikes(Predicate predicate, int page, int size,
      String direction,
      Comparator<MovieDto> comparing) {
    List<MovieDto> movies = getMovieDtos(predicate);
    if (direction.equals("desc")) {
      movies = movies.stream().sorted(comparing.reversed()).collect(Collectors.toList());
    } else { // asc
      movies = movies.stream().sorted(comparing).collect(Collectors.toList());
    }
    return getPage(page, size, movies);
  }

  /**
   * Retrieves the movies in cases of likes/hates sorting. with or without keyword.
   *
   * @param predicate if there is a keyword.
   * @return the list of movies.
   */
  private List<MovieDto> getMovieDtos(Predicate predicate) {
    return (Objects.nonNull(predicate) ? IterableUtils.toList(
            moviesRepository.findAll(predicate)).stream().map(getMapper()::mapToDTO)
        .collect(Collectors.toList())
        : moviesRepository.findAll().stream().map(getMapper()::mapToDTO)
            .collect(Collectors.toList()));
  }
}

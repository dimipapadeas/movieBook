package org.papadeas.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.papadeas.dto.MovieDto;
import org.papadeas.enums.Reaction;
import org.papadeas.services.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("api/movie")
public class MovieController {

    private final MovieService movieService;


    /**
     * Creates a new movie entry
     *
     * @param newMovie the new movie
     * @return MovieDto
     */
    @PostMapping
    public ResponseEntity<MovieDto> create(@RequestBody MovieDto newMovie) {
        return ResponseEntity.ok(movieService.create(newMovie));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getMoviesByDate(
            @RequestParam(name = "sort", defaultValue = "title") String sort,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(required = false) String filter) {
        return ResponseEntity.ok(movieService.getAllMovies(page, size, direction, sort, filter));
    }

    @GetMapping("/users/")
    public ResponseEntity<?> getMoviesOfUser(@PathVariable String userId,
                                             @RequestParam int page,
                                             @RequestParam int size,
                                             @RequestParam String direction,
                                             @RequestParam(required = false) String property) {
        return ResponseEntity.ok(movieService.getUsersMovies(page, size, direction, property, userId));
    }

}

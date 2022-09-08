package org.papadeas.mappers;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.papadeas.dto.MovieDto;
import org.papadeas.enums.Reaction;
import org.papadeas.model.Movie;
import org.papadeas.services.UserService;

import java.util.Objects;

@Mapper(componentModel = "spring", uses = {UserMapper.class, UserService.class})
public interface MovieMapper extends BaseMapper<Movie, MovieDto> {

    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "createdBy", source = "createdBy")
    Movie mapToEntity(MovieDto dto);


    @Override
    @Mappings({
            @Mapping(target = "createdBy", source = "createdBy.username"),
    })
    MovieDto mapToDTO(Movie entity);


    @AfterMapping
    default void afterMapToDto(@MappingTarget MovieDto dto, Movie movie) {

        if (Objects.nonNull(movie.getVotes()) && movie.getVotes().size() > 0) {

            dto.setDislikes(movie.getVotes().stream().filter(vote ->
                    vote.getVote().equals(Reaction.NEGATIVE)).count());

            dto.setLikes(movie.getVotes().stream().filter(vote ->
                    vote.getVote().equals(Reaction.POSITIVE)).count());
        }
    }

}

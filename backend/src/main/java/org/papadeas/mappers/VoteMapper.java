package org.papadeas.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.papadeas.dto.VoteDto;
import org.papadeas.model.Vote;
import org.papadeas.services.MovieService;
import org.papadeas.services.UserService;

@Mapper(componentModel = "spring", uses = {MovieMapper.class, UserMapper.class,
        MovieService.class, UserService.class})
public interface VoteMapper extends BaseMapper<Vote, VoteDto> {


    /**
     * Maps a VoteDto DTO to an entity Vote.
     *
     * @param dto the source DTO
     * @return the mapped entity
     */
    @Mapping(target = "user", source = "userId")
    @Mapping(target = "movie", source = "movieId")
    Vote mapToEntity(VoteDto dto);

    /**
     * Maps a Vote entity to a VoteDto
     *
     * @param entity the source entity
     * @return
     */
    @Override
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "movie.id", target = "movieId")
    VoteDto mapToDTO(Vote entity);

}

package org.papadeas.mappers;

import org.papadeas.dto.BaseDto;
import org.papadeas.model.EntityBase;

import java.util.List;

/**
 * Defines the basic mapping functions between an entity and a dto.
 *
 * @param <E> the entity
 * @param <D> the DTO
 */
public interface BaseMapper<E extends EntityBase, D extends BaseDto> {

    /**
     * Maps an entity to a DTO.
     *
     * @param entity the source entity
     * @return the mapped DTO
     */
    D mapToDTO(E entity);

    /**
     * Maps a list of entities to a list of DTO's.
     *
     * @param entity the source entities list
     * @return the mapped list of DTO's
     */
    List<D> mapToDTO(Iterable<E> entity);


    /**
     * Maps a DTO to an entity.
     *
     * @param dto the source DTO
     * @return the mapped entity
     */
    E mapToEntity(D dto);


}

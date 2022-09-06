package org.papadeas.services.interfaces;

import com.querydsl.core.types.Predicate;
import org.papadeas.dto.BaseDto;
import org.papadeas.model.EntityBase;
import org.springframework.data.domain.Page;

import java.util.List;


/**
 * An interface describing the basic functionality of app services
 *
 * @param <E> an app entity
 * @param <D> a app DTO
 */
public interface ServiceBase<E extends EntityBase, D extends BaseDto> {

    /**
     * Maps a DTO to a new entity persists it to the DB and returns the generated ID
     *
     * @param dto the DTO to be mapped to the created entity
     * @return the ID of the persisted entity
     */
    D create(D dto);

    /**
     * Updates an entity using id and version for matching
     *
     * @param dto the DTO containing all the information regarding the entity to be updated
     * @return a DTO with the updated entity and new version
     */
    D update(D dto);

    /**
     * Searches the DB based on the given predicate and return the results paginated in the form
     * requested
     *
     * @param page      the page requested
     * @param size      the size of the page
     * @param direction the Direction of sorting ASC or DESC
     * @param property  the property with which to sort
     * @param predicate the predicate which the search will be based on
     * @return a spring data page containing the search results
     */
    // TODO to be removed and use the findPaginated with more than one properties.
    Page<E> findPaginated(int page, int size, String direction, String property, Predicate predicate);

    /**
     * Searches the DB based on the given predicate and return the results paginated in the form
     * requested
     *
     * @param page       the page requested
     * @param size       the size of the page
     * @param direction  the Direction of sorting ASC or DESC
     * @param properties the list of properties with which to sort
     * @param predicate  the predicate which the search will be based on
     * @return a spring data page containing the search results
     */
    Page<E> findPaginated(int page, int size, String direction, Predicate predicate, String... properties);

    /**
     * Searches an entity in the DB and returns a DTO with the entity's properties
     *
     * @param id the ID of the entity to be searched
     * @return a DTO of the entity type
     * @throws Exception when the entity is not present in the DB
     */
    D findById(String id);

    /**
     * Searches an entity in the DB and returns it
     *
     * @param id the ID of the entity to be searched
     * @return the found entity or {@literal null} if the id given is {@literal null}
     * @throws Exception when the entity is not present in the DB
     */
    E findResource(String id);

    /**
     * Searches the DB based on the given predicate and return the results in a list
     *
     * @param predicate the predicate which the search will be based on
     * @return a java.util list containing the search results
     */
    List<E> findByPredicate(Predicate predicate);

    /**
     * Searches the DB and returns the count of the entities that match the given predicate
     *
     * @param predicate the predicate which the search will be based on
     * @return the count of the results
     */
    long countByPredicate(Predicate predicate);

    /**
     * Searches and deletes a record from the DB based on the given ID
     *
     * @param id the ID of the resource to be deleted
     */
    void delete(String id);


    /**
     * Indicates weather a resource with the given criteria is present in the DB
     *
     * @param predicate a Predicate containing the criteria
     * @return {@literal true} if the resource is present
     */
    boolean exists(Predicate predicate);

}

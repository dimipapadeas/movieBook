package org.papadeas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface GenericRepository<T> extends JpaRepository<T, Long>,
        QuerydslPredicateExecutor<T> {


    Optional<T> findById(String id);

    boolean existsById(String id);

    /**
     * Deletes an object based on its id
     *
     * @param id the id of the Object to delete
     */
    default void delete(String id) {
        findById(id).ifPresent(this::delete);
    }
}

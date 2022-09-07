package org.papadeas.repositories;


import org.papadeas.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GenericRepository<User> {

    /**
     * Retrieves a User Entity by username
     *
     * @param userName the name to find matching User
     * @return User
     */
    User findByUsername(String userName);


    /**
     * checks if a User with username exits
     *
     * @param userName the name to find matching User
     * @return true or false
     */
    boolean existsByUsername(String userName);
}

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
}

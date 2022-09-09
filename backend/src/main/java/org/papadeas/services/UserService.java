package org.papadeas.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.papadeas.dto.UserDto;
import org.papadeas.exception.AppGenericException;
import org.papadeas.mappers.UserMapper;
import org.papadeas.model.User;
import org.papadeas.model.security.UserPrincipal;
import org.papadeas.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService extends BaseService<User, UserDto> implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserMapper mapper;

    @PostConstruct
    public void onInit() {
        setMapper(mapper);
        setRepository(userRepository);
    }


    /**
     * Gets all the available Users currently in the System
     *
     * @return List<UserDto>
     */
    public List<UserDto> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return mapper.mapToDTO(userList);
    }

    /**
     * Gets a User by its id
     *
     * @param id the id of the User requested
     * @return UserDto
     */
    public UserDto getUserById(String id) {
        User user = userRepository.findById(id).orElse(null);
        return mapper.mapToDTO(user);
    }

    /**
     * Updates the User with the data from the Front end
     *
     * @param dto the updated User
     * @return UserDto
     */
    public UserDto updateUser(UserDto dto) throws AppGenericException {
        validateUserName(dto);
        User user = mapper.mapToEntity(dto);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(dto.getPassword()));
        userRepository.save(user);
        log.info("User {}, data was updated", user.getUsername());
        return mapper.mapToDTO(user);
    }


    /**
     * Deletes a User from the Database
     *
     * @param id the id of the User to delete
     */
    public void deleteUser(String id) {
        userRepository.delete(id);
        log.info("User with id :{} was deleted", id);
    }

    /**
     * Creates and returns a new empty User Object
     *
     * @return UserDto
     */
    public UserDto createDraftUser() {
        User draftUser = new User();
        return mapper.mapToDTO(draftUser);
    }

    /**
     * Gets a User by its Username
     *
     * @param userName the userName of the User requested
     * @return User
     */
    @Override
    public UserPrincipal loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User does not exist");
        }
        return new UserPrincipal(user);
    }

    /**
     * During a new registration checks if a username is already taken
     *
     * @param dto new user
     * @throws AppGenericException that user already exists
     */
    private void validateUserName(UserDto dto) throws AppGenericException {
        if (Objects.isNull(dto.getId())) {
            if (userRepository.existsByUsername(dto.getUsername())) {
                throw new AppGenericException("User already exists");
            }
        }
    }

}

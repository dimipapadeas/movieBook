package org.papadeas.services;


import lombok.RequiredArgsConstructor;
import org.papadeas.dto.UserDto;
import org.papadeas.mappers.UserMapper;
import org.papadeas.model.User;
import org.papadeas.model.security.UserPrincipal;
import org.papadeas.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

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
    public UserDto updateUser(UserDto dto) {
        User user = userRepository.findById(dto.getId()).orElse(null);
//        if (user != null) {
//            mapper.dtoToEntity(dto, user);
//        } else {
        user = mapper.mapToEntity(dto);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(dto.getPassword()));
//        }
        userRepository.save(user);
        return mapper.mapToDTO(user);
    }

    /**
     * Deletes an User from the Database
     *
     * @param id the id of the User to delete
     */
    public void deleteUser(String id) {
        userRepository.delete(id);
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
}

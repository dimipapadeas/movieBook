package org.papadeas.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.papadeas.dto.UserDto;
import org.papadeas.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    /**
     * Retrieves all available Users
     *
     * @return List<UserDTO>
     */
    @GetMapping
    public ResponseEntity<?> getUser() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Retrieves a User by its id
     *
     * @param userId the id of the User requested
     * @return UserDTO
     */
    @GetMapping("{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    /**
     * Updates the User with the new data
     *
     * @param user the updated User
     * @return UserDTO
     */
    @PutMapping
    public ResponseEntity<UserDto> update(@RequestBody UserDto user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    /**
     * Creates and returns a new empty User Object
     *
     * @return UserDTO
     */
    @GetMapping("_draft")
    public ResponseEntity<UserDto> createDraftUser() {
        return ResponseEntity.ok(userService.createDraftUser());
    }

    /**
     * Deletes a User
     *
     * @param userId the id of the User to delete
     */
    @DeleteMapping("{userId}")
    public void delete(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
    }
}


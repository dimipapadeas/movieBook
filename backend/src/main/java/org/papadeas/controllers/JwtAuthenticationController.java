package org.papadeas.controllers;

import lombok.RequiredArgsConstructor;
import org.papadeas.model.security.JwtRequest;
import org.papadeas.model.security.JwtResponse;
import org.papadeas.services.auth.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RequiredArgsConstructor
@RestController
public class JwtAuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Authenticates the User from the credentials provided during log in
     *
     * @param authenticationRequest provided credentials
     * @return ResponseEntity<?>
     * @throws Exception throws an Exception
     */
    @PostMapping("/api/authenticate")
    public ResponseEntity<?> generateAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        JwtResponse response = authenticationService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        return ResponseEntity.ok(response);
    }

    /**
     * Removes authenticated user from security context
     * @param username username of user account
     * @return ok Response
     */
    @PostMapping("/api/logout")
    public ResponseEntity<?> logout(@RequestBody String username) {
        JwtResponse response = authenticationService.logout(username);
        return ResponseEntity.ok(response);
    }
}

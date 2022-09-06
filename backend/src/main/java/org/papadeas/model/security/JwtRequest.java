package org.papadeas.model.security;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class JwtRequest implements Serializable {


    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    /**
     * Default constructor for JSON Parsing
     */
    public JwtRequest() {
        //TODO test with no @ Args Contractor
    }

    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
}

package org.papadeas.model.security;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
public class JwtRequest implements Serializable {


    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
}

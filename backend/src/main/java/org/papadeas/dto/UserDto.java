package org.papadeas.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UserDto extends BaseDto {


    private String username;

    private String password;

    private String firstName;

    private String lastName;

}

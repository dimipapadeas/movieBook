package org.papadeas.mappers;

import org.mapstruct.Mapper;
import org.papadeas.dto.UserDto;
import org.papadeas.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDto>{


}

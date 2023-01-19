package com.sgedts.wallet.mapper;

import com.sgedts.wallet.dto.UserDTO;
import com.sgedts.wallet.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserDto(User user);

    User toUser(UserDTO userDTO);
}

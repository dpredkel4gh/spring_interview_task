package com.yellow.test.mapper.user;

import com.yellow.test.entity.User;
import com.yellow.test.mapper.Mapper;
import com.yellow.test.model.user.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDTOMapper implements Mapper<User, UserDTO> {

    @Override
    public UserDTO map(User value) {
        if (value == null)
            return null;

        return UserDTO.builder()
                .uuid(value.getUuid())
                .username(value.getUsername())
                .active(value.isActive())
                .build();
    }
}

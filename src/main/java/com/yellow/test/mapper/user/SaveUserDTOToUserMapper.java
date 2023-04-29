package com.yellow.test.mapper.user;

import com.yellow.test.entity.Authority;
import com.yellow.test.entity.User;
import com.yellow.test.mapper.Mapper;
import com.yellow.test.model.user.SaveUserDTO;
import com.yellow.test.util.Generator;
import org.springframework.stereotype.Component;

@Component
public class SaveUserDTOToUserMapper implements Mapper<SaveUserDTO, User> {

    @Override
    public User map(SaveUserDTO value) {
        if (value == null)
            return null;

        return User.builder()
                .uuid(Generator.uuidAsString())
                .username(value.getUsername())
                .authority(Authority.USER)
                .active(true)
                .build();
    }
}

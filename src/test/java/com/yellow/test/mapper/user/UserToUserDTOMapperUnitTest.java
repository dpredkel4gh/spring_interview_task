package com.yellow.test.mapper.user;

import com.yellow.test.entity.User;
import com.yellow.test.model.user.UserDTO;
import com.yellow.test.util.Generator;
import com.yellow.test.util.TestGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
public class UserToUserDTOMapperUnitTest {

    @Spy
    private UserToUserDTOMapper mapper;

    @Test
    public void mapValueIsNull() {
        User running = null;
        UserDTO actualResult = mapper.map(running);
        assertNull(actualResult);
    }

    @Test
    public void map() {
        User user = User.builder()
                .uuid(Generator.uuidAsString())
                .username(TestGenerator.randomString())
                .active(true)
                .build();

        UserDTO actualResult = mapper.map(user);

        assertEquals(user.getUuid(), actualResult.getUuid());
        assertEquals(user.getUsername(), actualResult.getUsername());
        assertEquals(user.isActive(), actualResult.isActive());
    }

}




















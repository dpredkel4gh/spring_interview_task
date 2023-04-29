package com.yellow.test.mapper.user;

import com.yellow.test.entity.Authority;
import com.yellow.test.entity.User;
import com.yellow.test.model.user.SaveUserDTO;
import com.yellow.test.util.TestGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class SaveUserDTOToUserMapperUnitTest {

    @Spy
    private SaveUserDTOToUserMapper mapper;

    @Test
    public void mapValueIsNull() {
        SaveUserDTO running = null;
        User actualResult = mapper.map(running);
        assertNull(actualResult);
    }

    @Test
    public void map() {
        SaveUserDTO dto = SaveUserDTO.builder()
                .username(TestGenerator.randomString())
                .build();

        User actualResult = mapper.map(dto);

        assertNotNull(actualResult.getUuid());
        assertEquals(dto.getUsername(), actualResult.getUsername());
        assertNull(actualResult.getPassword());
        assertEquals(Authority.USER, actualResult.getAuthority());
        assertTrue(actualResult.isActive());
    }
}
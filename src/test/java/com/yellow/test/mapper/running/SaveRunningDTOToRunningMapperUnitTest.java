package com.yellow.test.mapper.running;

import com.yellow.test.entity.Running;
import com.yellow.test.model.running.SaveRunningDTO;
import com.yellow.test.util.TestGenerator;
import com.yellow.test.util.ld.LocalDateFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class SaveRunningDTOToRunningMapperUnitTest {

    @Spy
    private SaveRunningDTOToRunningMapper mapper;

    @Test
    public void mapValueIsNull() {
        SaveRunningDTO running = null;
        Running actualResult = mapper.map(running);
        assertNull(actualResult);
    }

    @Test
    public void map() {
        String date = "20190101";
        SaveRunningDTO dto = SaveRunningDTO.builder()
                .distance(TestGenerator.randomInt())
                .duration(TestGenerator.randomInt())
                .date(date)
                .build();

        Running actualResult = mapper.map(dto);

        assertNotNull(actualResult.getUuid());
        assertEquals(dto.getDistance(), actualResult.getDistance().intValue());
        assertEquals(dto.getDuration(), actualResult.getDuration().intValue());
        assertEquals(LocalDateFormatter.parse(dto.getDate()), actualResult.getDate());
        assertNull(actualResult.getUser());
    }
}
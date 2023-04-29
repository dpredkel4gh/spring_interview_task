package com.yellow.test.mapper.running;

import com.yellow.test.entity.Running;
import com.yellow.test.model.running.RunningDTO;
import com.yellow.test.util.Generator;
import com.yellow.test.util.TestGenerator;
import com.yellow.test.util.ld.LocalDateFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
public class RunningToRunningDTOMapperUnitTest {

    @Spy
    private RunningToRunningDTOMapper mapper;

    @Test
    public void mapValueIsNull() {
        Running running = null;
        RunningDTO actualResult = mapper.map(running);
        assertNull(actualResult);
    }

    @Test
    public void map() {
        LocalDate date = LocalDate.now();
        Running running = Running.builder()
                .uuid(Generator.uuidAsString())
                .distance(TestGenerator.randomInt())
                .duration(TestGenerator.randomInt())
                .date(date)
                .build();

        RunningDTO actualResult = mapper.map(running);

        assertEquals(running.getUuid(), actualResult.getUuid());
        assertEquals(running.getDistance(), actualResult.getDistance());
        assertEquals(running.getDuration(), actualResult.getDuration());
        assertEquals(LocalDateFormatter.format(running.getDate()), actualResult.getDate());
    }
}
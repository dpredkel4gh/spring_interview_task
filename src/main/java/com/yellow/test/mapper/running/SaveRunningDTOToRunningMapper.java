package com.yellow.test.mapper.running;

import com.yellow.test.entity.Running;
import com.yellow.test.mapper.Mapper;
import com.yellow.test.model.running.SaveRunningDTO;
import com.yellow.test.util.Generator;
import com.yellow.test.util.ld.LocalDateFormatter;
import org.springframework.stereotype.Component;

@Component
public class SaveRunningDTOToRunningMapper implements Mapper<SaveRunningDTO, Running> {

    @Override
    public Running map(SaveRunningDTO value) {
        if (value == null)
            return null;

        return Running.builder()
                .uuid(Generator.uuidAsString())
                .distance(value.getDistance())
                .duration(value.getDuration())
                .date(LocalDateFormatter.parse(value.getDate()))
                .build();
    }
}

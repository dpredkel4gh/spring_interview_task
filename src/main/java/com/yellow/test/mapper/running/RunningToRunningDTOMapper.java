package com.yellow.test.mapper.running;

import com.yellow.test.entity.Running;
import com.yellow.test.mapper.Mapper;
import com.yellow.test.model.running.RunningDTO;
import com.yellow.test.util.ld.LocalDateFormatter;
import org.springframework.stereotype.Component;

@Component
public class RunningToRunningDTOMapper implements Mapper<Running, RunningDTO> {

    @Override
    public RunningDTO map(Running value) {
        if (value == null)
            return null;

        return RunningDTO.builder()
                .uuid(value.getUuid())
                .distance(value.getDistance())
                .duration(value.getDuration())
                .date(LocalDateFormatter.format(value.getDate()))
                .build();
    }
}

package com.yellow.test.service.running;

import com.yellow.test.model.running.*;
import org.springframework.data.domain.Page;

public interface RunningService {

    RunningDTO save(SaveRunningDTO dto);

    RunningDTO update(UpdateRunningDTO dto);

    Page<RunningDTO> findByUserUuid(GetRunningListDTO dto);

    RunningDTO findByUuidAndUserUuid(GetRunningDTO dto);

    void deleteByUuidAndUserUuid(DeleteRunningDTO dto);
}

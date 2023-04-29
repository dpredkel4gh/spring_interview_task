package com.yellow.test.service.running;

import com.yellow.test.entity.Running;
import com.yellow.test.exception.Code;
import com.yellow.test.exception.ServiceRuntimeException;
import com.yellow.test.mapper.Mapper;
import com.yellow.test.model.running.*;
import com.yellow.test.repository.RunningRepository;
import com.yellow.test.repository.UserRepository;
import com.yellow.test.service.msg.MsgService;
import com.yellow.test.util.ld.LocalDateFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class RunningServiceImpl implements RunningService {

    @Autowired
    private MsgService msgService;

    @Autowired
    private RunningRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mapper<SaveRunningDTO, Running> mapper;

    @Autowired
    private Mapper<Running, RunningDTO> toDTOMapper;

    @Override
    public RunningDTO save(SaveRunningDTO dto) {
        Running running = mapper.map(dto);
        running.setUser(userRepository.findByUuid(dto.getUserUuid()));
        Running saved = repository.save(running);
        return toDTOMapper.map(saved);
    }

    @Override
    public RunningDTO update(UpdateRunningDTO dto) {
        Running existing = repository.findByUuidAndUserUuid(dto.getUuid(), dto.getUserUuid());
        if (existing == null)
            throw new ServiceRuntimeException(msgService.msg(Code.C_1003.getMsg(), dto.getUuid(), dto.getUserUuid()), Code.C_1003.getValue());

        existing.setDistance(dto.getDistance());
        existing.setDuration(dto.getDuration());
        existing.setDate(LocalDateFormatter.parse(dto.getDate()));

        Running saved = repository.save(existing);
        return toDTOMapper.map(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RunningDTO> findByUserUuid(GetRunningListDTO dto) {
        Page<Running> entities = repository.findByUserUuid(dto.getUserUuid(), dto.getPageable());
        return entities.map(toDTOMapper::map);
    }

    @Override
    @Transactional(readOnly = true)
    public RunningDTO findByUuidAndUserUuid(GetRunningDTO dto) {
        Running entity = repository.findByUuidAndUserUuid(dto.getUuid(), dto.getUserUuid());
        if (entity == null)
            throw new ServiceRuntimeException(msgService.msg(Code.C_1003.getMsg(), dto.getUuid(), dto.getUserUuid()), Code.C_1003.getValue());

        return toDTOMapper.map(entity);
    }

    @Override
    public void deleteByUuidAndUserUuid(DeleteRunningDTO dto) {
        Running entity = repository.findByUuidAndUserUuid(dto.getUuid(), dto.getUserUuid());
        if (entity == null)
            throw new ServiceRuntimeException(msgService.msg(Code.C_1003.getMsg(), dto.getUuid(), dto.getUserUuid()), Code.C_1003.getValue());

        repository.delete(entity);
    }

}

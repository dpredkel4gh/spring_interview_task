package com.yellow.test.service.running;

import com.yellow.test.constant.NumberConstant;
import com.yellow.test.entity.Running;
import com.yellow.test.entity.User;
import com.yellow.test.exception.ServiceRuntimeException;
import com.yellow.test.mapper.Mapper;
import com.yellow.test.model.running.*;
import com.yellow.test.repository.RunningRepository;
import com.yellow.test.repository.UserRepository;
import com.yellow.test.service.msg.MsgService;
import com.yellow.test.util.Generator;
import com.yellow.test.util.TestGenerator;
import com.yellow.test.util.ld.LocalDateFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class RunningServiceUnitTest {
    private static final String MOCK_DATE = "20190101";

    @InjectMocks
    private RunningServiceImpl service;

    @Mock
    private MsgService msgService;

    @Mock
    private RunningRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Mapper<SaveRunningDTO, Running> mapper;

    @Mock
    private Mapper<Running, RunningDTO> toDTOMapper;

    @Test
    public void save() {
        SaveRunningDTO dto = SaveRunningDTO.builder()
                .distance(ThreadLocalRandom.current().nextInt())
                .duration(ThreadLocalRandom.current().nextInt())
                .date(MOCK_DATE)
                .userUuid(Generator.uuidAsString())
                .build();
        Running mapped = Running.builder().build();
        User user = User.builder().build();
        Running saved = Running.builder().build();
        RunningDTO expectedResult = RunningDTO.builder().build();

        when(mapper.map(dto)).thenReturn(mapped);
        when(userRepository.findByUuid(dto.getUserUuid())).thenReturn(user);
        when(repository.save(mapped)).thenReturn(saved);
        when(toDTOMapper.map(saved)).thenReturn(expectedResult);

        RunningDTO actualResult = service.save(dto);

        assertEquals(user, mapped.getUser());
        assertEquals(expectedResult, actualResult);

        verify(mapper).map(dto);
        verify(userRepository).findByUuid(dto.getUserUuid());
        verify(repository).save(mapped);
        verify(toDTOMapper).map(saved);
    }

    @Test(expected = ServiceRuntimeException.class)
    public void updateRunningNotExists() {
        UpdateRunningDTO dto = UpdateRunningDTO.builder()
                .uuid(Generator.uuidAsString())
                .distance(ThreadLocalRandom.current().nextInt())
                .duration(ThreadLocalRandom.current().nextInt())
                .date(MOCK_DATE)
                .userUuid(Generator.uuidAsString())
                .build();

        when(repository.findByUuidAndUserUuid(dto.getUuid(), dto.getUserUuid())).thenReturn(null);
        when(msgService.msg(anyString(), anyCollection())).thenReturn(TestGenerator.randomString());

        service.update(dto);
    }

    @Test
    public void update() {
        UpdateRunningDTO dto = UpdateRunningDTO.builder()
                .uuid(Generator.uuidAsString())
                .distance(ThreadLocalRandom.current().nextInt())
                .duration(ThreadLocalRandom.current().nextInt())
                .date(MOCK_DATE)
                .userUuid(Generator.uuidAsString())
                .build();
        Running existing = Running.builder().build();
        Running saved = Running.builder().build();
        RunningDTO expectedResult = RunningDTO.builder().build();

        when(repository.findByUuidAndUserUuid(dto.getUuid(), dto.getUserUuid())).thenReturn(existing);
        when(repository.save(existing)).thenReturn(saved);
        when(toDTOMapper.map(saved)).thenReturn(expectedResult);

        RunningDTO actualResult = service.update(dto);

        assertEquals(dto.getDistance(), existing.getDistance());
        assertEquals(dto.getDuration(), existing.getDuration());
        assertEquals(LocalDateFormatter.parse(dto.getDate()), existing.getDate());
        assertEquals(expectedResult, actualResult);

        verify(repository).findByUuidAndUserUuid(dto.getUuid(), dto.getUserUuid());
        verify(msgService, times(NumberConstant.ZERO)).msg(anyString(), anyCollection());
        verify(repository).save(existing);
        verify(toDTOMapper).map(saved);
    }

    @Test
    public void findByUserUuid() {
        GetRunningListDTO dto = GetRunningListDTO.builder()
                .userUuid(Generator.uuidAsString())
                .pageable(Pageable.unpaged())
                .build();
        PageImpl<Running> page = new PageImpl<>(
                Arrays.asList(
                        Running.builder()
                                .uuid(Generator.uuidAsString())
                                .date(LocalDate.now())
                                .distance(ThreadLocalRandom.current().nextInt())
                                .duration(ThreadLocalRandom.current().nextInt())
                                .build(),
                        Running.builder()
                                .uuid(Generator.uuidAsString())
                                .date(LocalDate.now())
                                .distance(ThreadLocalRandom.current().nextInt())
                                .duration(ThreadLocalRandom.current().nextInt())
                                .build()
                )
        );
        when(repository.findByUserUuid(dto.getUserUuid(), dto.getPageable())).thenReturn(page);

        Page<RunningDTO> actualResult = service.findByUserUuid(dto);

        assertEquals(page.getNumberOfElements(), actualResult.getNumberOfElements());

        verify(repository).findByUserUuid(dto.getUserUuid(), dto.getPageable());
        verify(toDTOMapper, times(page.getNumberOfElements())).map(any(Running.class));

        for (Running running : page.getContent()) {
            verify(toDTOMapper).map(running);
        }
    }

    @Test(expected = ServiceRuntimeException.class)
    public void findByUuidAndUserUuidRunningNotExists() {
        GetRunningDTO dto = GetRunningDTO.builder()
                .uuid(Generator.uuidAsString())
                .userUuid(Generator.uuidAsString())
                .build();

        when(repository.findByUuidAndUserUuid(dto.getUuid(), dto.getUserUuid())).thenReturn(null);
        when(msgService.msg(anyString(), anyCollection())).thenReturn(TestGenerator.randomString());

        service.findByUuidAndUserUuid(dto);
    }

    @Test
    public void findByUuidAndUserUuid() {
        GetRunningDTO dto = GetRunningDTO.builder()
                .uuid(Generator.uuidAsString())
                .userUuid(Generator.uuidAsString())
                .build();
        Running existing = Running.builder().build();
        RunningDTO expectedResult = RunningDTO.builder().build();

        when(repository.findByUuidAndUserUuid(dto.getUuid(), dto.getUserUuid())).thenReturn(existing);
        when(toDTOMapper.map(existing)).thenReturn(expectedResult);

        RunningDTO actualResult = service.findByUuidAndUserUuid(dto);

        assertEquals(expectedResult, actualResult);

        verify(repository).findByUuidAndUserUuid(dto.getUuid(), dto.getUserUuid());
        verify(msgService, times(NumberConstant.ZERO)).msg(anyString(), anyCollection());
        verify(toDTOMapper).map(existing);
    }

    @Test(expected = ServiceRuntimeException.class)
    public void deleteByUuidAndUserUuidRunningNotExists() {
        DeleteRunningDTO dto = DeleteRunningDTO.builder()
                .uuid(Generator.uuidAsString())
                .userUuid(Generator.uuidAsString())
                .build();

        when(repository.findByUuidAndUserUuid(dto.getUuid(), dto.getUserUuid())).thenReturn(null);
        when(msgService.msg(anyString(), anyCollection())).thenReturn(TestGenerator.randomString());

        service.deleteByUuidAndUserUuid(dto);
    }

    @Test
    public void deleteByUuidAndUserUuid() {
        DeleteRunningDTO dto = DeleteRunningDTO.builder()
                .uuid(Generator.uuidAsString())
                .userUuid(Generator.uuidAsString())
                .build();
        Running existing = Running.builder().build();

        when(repository.findByUuidAndUserUuid(dto.getUuid(), dto.getUserUuid())).thenReturn(existing);

        service.deleteByUuidAndUserUuid(dto);

        verify(repository).findByUuidAndUserUuid(dto.getUuid(), dto.getUserUuid());
        verify(msgService, times(NumberConstant.ZERO)).msg(anyString(), anyCollection());
        verify(repository).delete(existing);
    }

}
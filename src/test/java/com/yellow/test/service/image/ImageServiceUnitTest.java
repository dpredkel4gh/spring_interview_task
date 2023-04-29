package com.yellow.test.service.image;

import com.yellow.test.constant.NumberConstant;
import com.yellow.test.entity.Image;
import com.yellow.test.entity.User;
import com.yellow.test.exception.ServiceRuntimeException;
import com.yellow.test.mapper.Mapper;
import com.yellow.test.model.image.*;
import com.yellow.test.repository.ImageRepository;
import com.yellow.test.repository.UserRepository;
import com.yellow.test.service.msg.MsgService;
import com.yellow.test.util.Generator;
import com.yellow.test.util.TestGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ImageServiceUnitTest {

    @InjectMocks
    private ImageServiceImpl service;

    @Mock
    private MsgService msgService;

    @Mock
    private ImageRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Mapper<SaveImageDTO, Image> mapper;

    @Mock
    private Mapper<Image, ImageDTO> toDTOMapper;

    @Mock
    private Mapper<Image, DataImageDTO> toDataDTOMapper;

    @Test
    public void save() {
        SaveImageDTO dto = SaveImageDTO.builder()
                .filename(TestGenerator.randomString())
                .contentType(TestGenerator.randomString())
                .data(new byte[0])
                .userUuid(Generator.uuidAsString())
                .build();
        Image mapped = Image.builder().build();
        User user = User.builder().build();
        Image saved = Image.builder().build();
        ImageDTO expectedResult = ImageDTO.builder().build();

        when(mapper.map(dto)).thenReturn(mapped);
        when(userRepository.findByUuid(dto.getUserUuid())).thenReturn(user);
        when(repository.save(mapped)).thenReturn(saved);
        when(toDTOMapper.map(saved)).thenReturn(expectedResult);

        ImageDTO actualResult = service.save(dto);

        assertEquals(user, mapped.getUser());
        assertEquals(expectedResult, actualResult);

        verify(mapper).map(dto);
        verify(userRepository).findByUuid(dto.getUserUuid());
        verify(repository).save(mapped);
        verify(toDTOMapper).map(saved);
    }

    @Test
    public void findByUserUuid() {
        GetImageListDTO dto = GetImageListDTO.builder()
                .userUuid(Generator.uuidAsString())
                .pageable(Pageable.unpaged())
                .build();
        PageImpl<Image> page = new PageImpl<>(
                Arrays.asList(
                        Image.builder()
                                .uuid(Generator.uuidAsString())
                                .filename(Generator.uuidAsString())
                                .contentType(Generator.uuidAsString())
                                .data(new byte[0])
                                .build(),
                        Image.builder()
                                .uuid(Generator.uuidAsString())
                                .filename(Generator.uuidAsString())
                                .contentType(Generator.uuidAsString())
                                .data(new byte[0])
                                .build()
                )
        );
        when(repository.findByUserUuid(dto.getUserUuid(), dto.getPageable())).thenReturn(page);

        Page<ImageDTO> actualResult = service.findByUserUuid(dto);

        assertEquals(page.getNumberOfElements(), actualResult.getNumberOfElements());

        verify(repository).findByUserUuid(dto.getUserUuid(), dto.getPageable());
        verify(toDTOMapper, times(page.getNumberOfElements())).map(any(Image.class));

        for (Image image : page.getContent()) {
            verify(toDTOMapper).map(image);
        }
    }

    @Test(expected = ServiceRuntimeException.class)
    public void findByUuidAndUserUuidImageNotExists() {
        GetImageDTO dto = GetImageDTO.builder()
                .uuid(Generator.uuidAsString())
                .userUuid(Generator.uuidAsString())
                .build();

        when(repository.findByUuidAndUserUuid(dto.getUuid(), dto.getUserUuid())).thenReturn(null);
        when(msgService.msg(anyString(), anyCollection())).thenReturn(TestGenerator.randomString());

        service.findByUuidAndUserUuid(dto);
    }

    @Test
    public void findByUuidAndUserUuid() {
        GetImageDTO dto = GetImageDTO.builder()
                .uuid(Generator.uuidAsString())
                .userUuid(Generator.uuidAsString())
                .build();
        Image existing = Image.builder().build();
        DataImageDTO expectedResult = DataImageDTO.builder().build();

        when(repository.findByUuidAndUserUuid(dto.getUuid(), dto.getUserUuid())).thenReturn(existing);
        when(toDataDTOMapper.map(existing)).thenReturn(expectedResult);

        DataImageDTO actualResult = service.findByUuidAndUserUuid(dto);

        assertEquals(expectedResult, actualResult);

        verify(repository).findByUuidAndUserUuid(dto.getUuid(), dto.getUserUuid());
        verify(msgService, times(NumberConstant.ZERO)).msg(anyString(), anyCollection());
        verify(toDataDTOMapper).map(existing);
    }
}
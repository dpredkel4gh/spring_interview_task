package com.yellow.test.service.image;

import com.yellow.test.entity.Image;
import com.yellow.test.exception.Code;
import com.yellow.test.exception.ServiceRuntimeException;
import com.yellow.test.mapper.Mapper;
import com.yellow.test.model.image.*;
import com.yellow.test.repository.ImageRepository;
import com.yellow.test.repository.UserRepository;
import com.yellow.test.service.msg.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    @Autowired
    private MsgService msgService;

    @Autowired
    private ImageRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mapper<SaveImageDTO, Image> mapper;

    @Autowired
    private Mapper<Image, ImageDTO> toDTOMapper;

    @Autowired
    private Mapper<Image, DataImageDTO> toDataDTOMapper;

    @Override
    public ImageDTO save(SaveImageDTO dto) {
        Image image = mapper.map(dto);
        image.setUser(userRepository.findByUuid(dto.getUserUuid()));
        Image saved = repository.save(image);
        return toDTOMapper.map(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImageDTO> findByUserUuid(GetImageListDTO dto) {
        Page<Image> entities = repository.findByUserUuid(dto.getUserUuid(), dto.getPageable());
        return entities.map(running -> toDTOMapper.map(running));
    }

    @Override
    @Transactional(readOnly = true)
    public DataImageDTO findByUuidAndUserUuid(GetImageDTO dto) {
        Image entity = repository.findByUuidAndUserUuid(dto.getUuid(), dto.getUserUuid());
        if (entity == null)
            throw new ServiceRuntimeException(msgService.msg(Code.C_1004.getMsg(), dto.getUuid(), dto.getUserUuid()), Code.C_1004.getValue());

        return toDataDTOMapper.map(entity);
    }
}

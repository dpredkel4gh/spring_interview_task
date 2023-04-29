package com.yellow.test.mapper.image;

import com.yellow.test.entity.Image;
import com.yellow.test.mapper.Mapper;
import com.yellow.test.model.image.ImageDTO;
import org.springframework.stereotype.Component;

@Component
public class ImageToImageDTOMapper implements Mapper<Image, ImageDTO> {

    @Override
    public ImageDTO map(Image value) {
        if (value == null)
            return null;

        return ImageDTO.builder()
                .uuid(value.getUuid())
                .filename(value.getFilename())
                .contentType(value.getContentType())
                .build();
    }
}

package com.yellow.test.mapper.image;

import com.yellow.test.entity.Image;
import com.yellow.test.mapper.Mapper;
import com.yellow.test.model.image.DataImageDTO;
import org.springframework.stereotype.Component;

@Component
public class ImageToDataImageDTOMapper implements Mapper<Image, DataImageDTO> {

    @Override
    public DataImageDTO map(Image value) {
        if (value == null)
            return null;

        return DataImageDTO.builder()
                .uuid(value.getUuid())
                .filename(value.getFilename())
                .contentType(value.getContentType())
                .data(value.getData())
                .build();
    }
}

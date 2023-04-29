package com.yellow.test.mapper.image;

import com.yellow.test.entity.Image;
import com.yellow.test.mapper.Mapper;
import com.yellow.test.model.image.SaveImageDTO;
import com.yellow.test.util.Generator;
import org.springframework.stereotype.Component;

@Component
public class SaveImageDTOToImageMapper implements Mapper<SaveImageDTO, Image> {

    @Override
    public Image map(SaveImageDTO value) {
        if (value == null)
            return null;

        return Image.builder()
                .uuid(Generator.uuidAsString())
                .filename(value.getFilename())
                .contentType(value.getContentType())
                .data(value.getData())
                .build();
    }
}

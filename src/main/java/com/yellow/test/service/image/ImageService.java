package com.yellow.test.service.image;

import com.yellow.test.model.image.*;
import org.springframework.data.domain.Page;

public interface ImageService {
    ImageDTO save(SaveImageDTO dto);

    Page<ImageDTO> findByUserUuid(GetImageListDTO dto);

    DataImageDTO findByUuidAndUserUuid(GetImageDTO dto);
}

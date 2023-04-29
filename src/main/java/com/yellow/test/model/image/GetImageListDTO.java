package com.yellow.test.model.image;

import lombok.*;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetImageListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userUuid;
    private Pageable pageable;

}

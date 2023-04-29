package com.yellow.test.model.image;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetImageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String uuid;
    private String userUuid;
}

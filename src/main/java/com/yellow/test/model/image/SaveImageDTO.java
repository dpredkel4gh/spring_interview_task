package com.yellow.test.model.image;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveImageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String filename;
    private String contentType;
    private byte[] data;
    private String userUuid;
}

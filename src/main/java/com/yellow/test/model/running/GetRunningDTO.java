package com.yellow.test.model.running;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRunningDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String uuid;
    private String userUuid;
}

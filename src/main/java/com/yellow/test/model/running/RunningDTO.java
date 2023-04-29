package com.yellow.test.model.running;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RunningDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("distance")
    private Integer distance;

    @JsonProperty("duration")
    private Integer duration;

    @JsonProperty("date")
    private String date;

}

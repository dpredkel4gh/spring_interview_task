package com.yellow.test.model.running;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveRunningDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    @JsonProperty("distance")
    private int distance;

    @Min(1)
    @JsonProperty("duration")
    private int duration;

    @NotBlank
    // TODO: 2/16/2019 Add pattern for date
    @JsonProperty("date")
    private String date;

    // TODO: 2/16/2019 Ignore by swagger
    @JsonProperty("userUuid")
    private String userUuid;
}

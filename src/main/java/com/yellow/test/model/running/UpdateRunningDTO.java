package com.yellow.test.model.running;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRunningDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @JsonProperty("uuid")
    private String uuid;

    @Min(1)
    @NotNull
    @JsonProperty("distance")
    private Integer distance;

    @Min(1)
    @NotNull
    @JsonProperty("duration")
    private Integer duration;

    @NotBlank
    // TODO: 2/16/2019 Add pattern for date
    @JsonProperty("date")
    private String date;

    // TODO: 2/16/2019 Ignore by swagger
    @JsonProperty("userUuid")
    private String userUuid;
}

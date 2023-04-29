package com.yellow.test.model.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("averageSpeed")
    private BigDecimal averageSpeed;

    @JsonProperty("averageTime")
    private BigDecimal averageTime;

    @JsonProperty("totalDistance")
    private BigDecimal totalDistance;

    @JsonProperty("begin")
    private String begin;

    @JsonProperty("end")
    private String end;
}

package com.yellow.test.model.report;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetWeekReportDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int weekOffset;
    private String userUuid;

}

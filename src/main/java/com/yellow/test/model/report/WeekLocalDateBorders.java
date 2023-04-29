package com.yellow.test.model.report;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeekLocalDateBorders implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDate begin;
    private LocalDate end;
}

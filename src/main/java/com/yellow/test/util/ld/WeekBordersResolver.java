package com.yellow.test.util.ld;

import com.yellow.test.model.report.WeekLocalDateBorders;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeekBordersResolver {
    private static final int DAYS_OF_WEEK = 7;

    public static WeekLocalDateBorders getByOffset(int offset) {
        LocalDate date = LocalDate.now();
        return getWeekBorders(date.minusWeeks(offset));
    }

    private static WeekLocalDateBorders getWeekBorders(LocalDate date) {
        LocalDate begin;
        if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            begin = date;
        } else {
            begin = date.minusDays(date.getDayOfWeek().getValue());
        }
        LocalDate end = begin.plusDays(DAYS_OF_WEEK - 1);

        return WeekLocalDateBorders.builder()
                .begin(begin)
                .end(end)
                .build();
    }

}

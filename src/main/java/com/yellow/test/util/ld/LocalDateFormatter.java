package com.yellow.test.util.ld;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalDateFormatter {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd").withLocale(Locale.US);
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withLocale(Locale.US);

    public static LocalDate parse(String str) {
        return LocalDate.parse(str, formatter);
    }

    public static String format(LocalDate date) {
        return formatter.format(date);
    }

    public static String format(LocalDateTime time) {
        return timeFormatter.format(time);
    }

}

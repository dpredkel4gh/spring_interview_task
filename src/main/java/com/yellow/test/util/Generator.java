package com.yellow.test.util;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Generator {
    private static final NoArgGenerator UUID_GENERATOR = Generators.timeBasedGenerator();

    public static UUID uuid() {
        return UUID_GENERATOR.generate();
    }

    public static String uuidAsString() {
        return uuid().toString();
    }

}
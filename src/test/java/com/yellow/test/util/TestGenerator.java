package com.yellow.test.util;

import java.util.concurrent.ThreadLocalRandom;

public class TestGenerator {

    public static int randomInt() {
        return ThreadLocalRandom.current().nextInt();
    }

    public static String randomString() {
        return randomString(10);
    }

    /**
     * The results are not cryptographically secure!!!
     * This method is just to get test random strings.
     */
    private static String randomString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        StringBuilder buffer = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int) (ThreadLocalRandom.current().nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}

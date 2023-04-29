package com.yellow.test.util.bd;

import com.yellow.test.constant.NumberConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BigDecimalFactory {

    private static final RoundingMode ROUNDING_MODE = RoundingMode.FLOOR;
    public static final MathContext MATH_CONTEXT = new MathContext(NumberConstant.ZERO, ROUNDING_MODE);

    public static BigDecimal create(int value) {
        return new BigDecimal(value, MATH_CONTEXT);
    }

    public static BigDecimal create(double value) {
        return create(Double.toString(value));
    }

    public static BigDecimal create(String value) {
        return new BigDecimal(value, MATH_CONTEXT);
    }

    public static BigDecimal create(BigInteger value) {
        return new BigDecimal(value, MATH_CONTEXT);
    }
}

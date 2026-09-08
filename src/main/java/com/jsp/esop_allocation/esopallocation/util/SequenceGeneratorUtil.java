package com.jsp.esop_allocation.esopallocation.util;

import java.math.BigInteger;

public class SequenceGeneratorUtil {
    public static BigInteger generateAltKey() {
        return BigInteger.valueOf(100 + (long)(Math.random() * 45000)); // generate random number between 100 and 450099
    }
}
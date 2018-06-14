package com.rayzr522.cubehomes.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArrayUtils {
    public static String concat(Object[] arr, String filler) {
        return Arrays.stream(arr).map(Objects::toString).collect(Collectors.joining(filler));
    }
}

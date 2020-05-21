package com.funkypanda.hiring.constants;


import com.funkypanda.hiring.model.Game;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BetOdds {
    public static final List<String> betTypes = Arrays.asList("column", "redorblack", "oddoreven", "number");

    public static final List<Integer> red = Arrays.asList(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36);
    public static final List<Integer> black = Arrays.asList(2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35);
    public static final int redOrBlackOdds = 1;

    public static final List<Integer> column1 = Arrays.asList(1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34);
    public static final List<Integer> column2 = Arrays.asList(2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35);
    public static final List<Integer> column3 = Arrays.asList(3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36);
    public static final int columnOdds = 2;

    public static final int oddOrEvenOdds = 1;
    public static final int numberOdds = 36;

}
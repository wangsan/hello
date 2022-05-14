package top.sailingsan.leetcode.s0017;/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * created by wangsan on 2022/03/19.
 *
 * @author wangsan
 */
class SolutionTest {
    Solution solution = new Solution();

    @Test
    public void test() {
        assertArrayEquals(Arrays.stream(new String[] {"a", "b", "c"}).sorted().toArray(),
                solution.letterCombinations("2").stream().sorted().toArray());

        assertArrayEquals(
                Arrays.stream(new String[] {"ad", "bd", "cd", "ae", "be", "ce", "af", "bf", "cf"}).sorted().toArray(),
                solution.letterCombinations("23").stream().sorted().toArray());
    }

}

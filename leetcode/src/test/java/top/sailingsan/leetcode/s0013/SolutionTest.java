/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0013;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(3, solution.romanToInt("III"));
        assertEquals(4, solution.romanToInt("IV"));
        assertEquals(9, solution.romanToInt("IX"));
        assertEquals(58, solution.romanToInt("LVIII"));
        assertEquals(1994, solution.romanToInt("MCMXCIV"));
    }

}
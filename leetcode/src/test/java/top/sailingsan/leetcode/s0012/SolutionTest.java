/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0012;

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
        assertEquals("III", solution.intToRoman(3));
        assertEquals("IV", solution.intToRoman(4));
        assertEquals("IX", solution.intToRoman(9));
        assertEquals("LVIII", solution.intToRoman(58));
        assertEquals("MCMXCIV", solution.intToRoman(1994));
    }

}
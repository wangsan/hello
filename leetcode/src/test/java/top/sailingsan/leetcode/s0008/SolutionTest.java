/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0008;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SolutionTest {
    Solution solution = new Solution();

    @Test
    public void test() {
        assertEquals(0, solution.myAtoi(" b11228552307"));
        assertEquals(0, solution.myAtoi("b11228552307"));
        assertEquals(321, solution.myAtoi("321"));
        assertEquals(-42, solution.myAtoi("   -42"));
        assertEquals(4193, solution.myAtoi("4193 with words"));
        assertEquals(321, solution.myAtoi("+321"));
        assertEquals(0, solution.myAtoi("words and 987"));
        assertEquals(0, solution.myAtoi("+-12"));
        assertEquals(0, solution.myAtoi("00000-42a1234"));
        assertEquals(3, solution.myAtoi("3.14159"));
        assertEquals(0, solution.myAtoi("   +0 123"));
        assertEquals(0, solution.myAtoi("0 123"));
        assertEquals(-2147483648, solution.myAtoi("-2147483649"));
        assertEquals(-2147483647, solution.myAtoi("-2147483647"));
        assertEquals(123, solution.myAtoi("123-"));
    }

}
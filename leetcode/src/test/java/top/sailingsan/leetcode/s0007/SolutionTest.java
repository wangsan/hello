/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0007;

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
        assertEquals(123, solution.reverse(321));
        assertEquals(21, solution.reverse(120));
        assertEquals(-321, solution.reverse(-123));
        assertEquals(0, solution.reverse(0));
        assertEquals(0, solution.reverse(1534236469));
    }

}
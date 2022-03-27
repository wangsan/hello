/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0011;

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
        assertEquals(1, solution.maxArea(new int[] {1, 1}));
        assertEquals(49, solution.maxArea(new int[] {1, 8, 6, 2, 5, 4, 8, 3, 7}));
    }

}
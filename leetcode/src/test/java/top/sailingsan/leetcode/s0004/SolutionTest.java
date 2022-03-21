/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */

package top.sailingsan.leetcode.s0004;

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
        assertEquals(solution.findMedianSortedArrays(new int[] {1, 3}, new int[] {2}), 2.0, 0.01);
        assertEquals(solution.findMedianSortedArrays(new int[] {1, 2}, new int[] {3, 4}), 2.5, 0.01);
    }

}
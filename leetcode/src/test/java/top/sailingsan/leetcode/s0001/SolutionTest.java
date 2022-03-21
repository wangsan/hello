/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0001;

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
        arrayEquals(solution.twoSum(new int[] {2, 7, 11, 15}, 9), new int[] {0, 1});
        arrayEquals(solution.twoSum(new int[] {3, 2, 4}, 6), new int[] {1, 2});
        arrayEquals(solution.twoSum(new int[] {3, 3}, 6), new int[] {0, 1});
    }

    void arrayEquals(int[] a, int[] b) {
        int s = 0;
        for (int i : a) {
            s = s ^ i;
        }
        for (int i : b) {
            s = s ^ i;
        }
        assertEquals(s, 0);
    }
}
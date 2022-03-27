/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0014;

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
        assertEquals("fl", solution.longestCommonPrefix(new String[] {"flower", "flow", "flight"}));
        assertEquals("", solution.longestCommonPrefix(new String[] {"dog", "racecar", "car"}));
        assertEquals("a", solution.longestCommonPrefix(new String[] {"a"}));
        assertEquals("", solution.longestCommonPrefix(new String[] {"a", "b"}));
        assertEquals("a", solution.longestCommonPrefix(new String[] {"ab", "a"}));
        assertEquals("flower", solution.longestCommonPrefix(new String[] {"flower", "flower", "flower", "flower"}));

    }

}
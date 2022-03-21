/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0003;

import org.junit.jupiter.api.Assertions;
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
        Assertions.assertEquals(3, solution.lengthOfLongestSubstring("abcabcbb"));
        Assertions.assertEquals(1, solution.lengthOfLongestSubstring("bbbbb"));
        Assertions.assertEquals(3, solution.lengthOfLongestSubstring("pwwkew"));
    }

}
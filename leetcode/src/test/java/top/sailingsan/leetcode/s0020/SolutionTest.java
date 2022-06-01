package top.sailingsan.leetcode.s0020;/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */

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
        Assertions.assertTrue(solution.isValid("()"));
        Assertions.assertTrue(solution.isValid("()[]{}"));
        Assertions.assertTrue(solution.isValid("{[]}"));
        Assertions.assertFalse(solution.isValid("(]"));
        Assertions.assertFalse(solution.isValid("([)]"));
    }

}

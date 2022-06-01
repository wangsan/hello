package top.sailingsan.leetcode.s0019;/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import top.sailingsan.leetcode.ListNode;

/**
 * created by wangsan on 2022/03/19.
 *
 * @author wangsan
 */
class SolutionTest {
    Solution solution = new Solution();

    @Test
    public void test() {
        ListNode x = new ListNode(1, new ListNode(2));
        Assertions.assertEquals(new ListNode(1), solution.removeNthFromEnd(x, 1));

        x = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        Assertions.assertEquals(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(5)))),
                solution.removeNthFromEnd(x, 2));

        Assertions.assertEquals(null, solution.removeNthFromEnd(new ListNode(1), 1));

        Assertions.assertEquals(new ListNode(2), solution.removeNthFromEnd(new ListNode(1, new ListNode(2)), 2));

    }

}

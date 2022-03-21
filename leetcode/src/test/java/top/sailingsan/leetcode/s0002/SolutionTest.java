/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0002;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
        ListNode l3 = new ListNode(7, new ListNode(0, new ListNode(8)));
        assertEquals(solution.addTwoNumbers(l1, l2), l3);

        l1 = new ListNode(0);
        l2 = new ListNode(0);
        l3 = new ListNode(0);
        assertEquals(solution.addTwoNumbers(new ListNode(0), new ListNode(0)), new ListNode(0));

        l1 = new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9))));
        l2 = new ListNode(9, new ListNode(9));
        l3 = new ListNode(8, new ListNode(9, new ListNode(0, new ListNode(0, new ListNode(1)))));
        assertEquals(solution.addTwoNumbers(l1, l2), l3);
    }

}
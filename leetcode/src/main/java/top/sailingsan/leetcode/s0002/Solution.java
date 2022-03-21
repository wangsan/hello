/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0002;

import top.sailingsan.leetcode.ListNode;

/**
 * 模拟计算
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode();

        int carry = 0;
        ListNode r = head;
        while (l1 != null || l2 != null || carry != 0) {
            int a = l1 == null ? 0 : l1.val;
            int b = l2 == null ? 0 : l2.val;
            int val = (a + b + carry) % 10;
            r.next = new ListNode(val);

            carry = (a + b + carry) / 10;
            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
            r = r.next;
        }
        return head.next;
    }
}


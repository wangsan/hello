package top.sailingsan.leetcode.s0019;

import top.sailingsan.leetcode.ListNode;

class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode x = head;
        int length = 1;
        while (x.next != null) {
            x = x.next;
            length++;
        }

        if (length == n) {
            return head.next;
        }

        ListNode y = head;
        int i = 1;
        while (y.next != null) {
            if (i == length - n) {
                y.next = y.next.next;
                break;
            }

            y = y.next;
            i++;
        }

        return head;
    }

    public ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode first = head;
        ListNode second = dummy;
        for (int i = 0; i < n; ++i) {
            first = first.next;
        }
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        ListNode ans = dummy.next;
        return ans;
    }
}

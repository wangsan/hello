/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0007;

class Solution {
    public int reverse(int x) {
        int a = 0;
        while (x != 0) {
            int tmp = x % 10;
            if (a > Integer.MAX_VALUE / 10 || (a == Integer.MAX_VALUE / 10 && tmp > Integer.MAX_VALUE % 10)) {
                return 0;
            }
            if (a < Integer.MIN_VALUE / 10 || (a == Integer.MIN_VALUE / 10 && tmp < Integer.MIN_VALUE % 10)) {
                return 0;
            }
            a = a * 10 + tmp;
            x = x / 10;
        }
        return a;
    }
}
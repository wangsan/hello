/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0008;

class Solution {
    public int myAtoi(String s) {
        int a = 0;
        boolean number = false;
        boolean flag = false;
        boolean space = false;
        int negative = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean unValid = (c < '0' || c > '9') && c != '-' && c != '+';
            if (i == 0) {
                if (c == ' ') {
                    space = true;
                    continue;
                }
                if (unValid) {
                    break;
                }
            }

            if (space) {
                if (c == ' ') {
                    continue;
                }
                if (unValid) {
                    break;
                }
                space = false;
            }

            if (number && (c < '0' || c > '9')) {
                break;
            }

            if (flag && (c < '0' || c > '9')) {
                break;
            }
            if (c == '-') {
                negative = -1;
                flag = true;
                continue;
            }
            if (c == '+') {
                flag = true;
                continue;
            }

            if (c >= '0' && c <= '9') {
                int tmp = (c - '0');
                if (a > Integer.MAX_VALUE / 10 || (a == Integer.MAX_VALUE / 10 && tmp > Integer.MAX_VALUE % 10)) {
                    return Integer.MAX_VALUE;
                }
                if (a < Integer.MIN_VALUE / 10 || (a == Integer.MIN_VALUE / 10 && tmp > -(Integer.MIN_VALUE % 10))) {
                    return Integer.MIN_VALUE;
                }
                a = a * 10 + negative * tmp;
                number = true;
            }
        }

        return a;
    }
}
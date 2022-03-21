/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0009;

/**
 * 12321
 * <p>
 * 1
 * 12
 */
class Solution {
    public boolean isPalindrome(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        int r = 0;
        while (x > r) {
            r = r * 10 + x % 10;
            x = x / 10;
        }
        return x == r || x == r / 10;

        //        int d = 1;
        //        while (x / d > 9) {
        //            d = d * 10;
        //        }
        //
        //        while (x > 0) {
        //            int left = x / d;
        //            int right = x % 10;
        //            if (left != right) {
        //                return false;
        //            }
        //            x = x % d / 10;
        //            d = d / 100;
        //        }
        //
        //        return true;

        //        String a = x + "";
        //        boolean flag = true;
        //        for (int i = 0; i < a.length() / 2; i++) {
        //            if (a.charAt(i) != a.charAt(a.length() - 1 - i)) {
        //                flag = false;
        //                break;
        //            }
        //        }
        //        return flag;
    }
}
/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0006;

/**
 * 找规律
 * <p>
 * PAYPALISHIRING
 * <p>
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * <p>
 * PAHNAPLSIIGYIR
 * <p>
 * PAYPALISHIRING
 * <p>
 * P     I    N
 * A   L S  I G
 * Y A   H R
 * P     I
 * <p>
 * 0123456789abc
 * <p>
 * 0   4   8   c
 * 1 3 5 7 9 b
 * 2   6   a
 * <p>
 * 0     6     c
 * 1   5 7   b
 * 2 4   8 a
 * 3     9
 */
class Solution {
    public String convert(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }

        int y = 2 * numRows - 2;
        char[] chars = new char[s.length()];
        int a = 0;

        for (int i = 0; i < s.length(); i = i + y) {
            chars[a++] = s.charAt(i);
        }
        for (int j = 1; j < numRows - 1; j++) {
            for (int i = 0; i < s.length(); i = i + y) {
                if (i + j < s.length()) {
                    chars[a++] = s.charAt(i + j);
                }
                if (i + y - j < s.length()) {
                    chars[a++] = s.charAt(i + y - j);
                }
            }
        }
        for (int i = numRows - 1; i < s.length(); i = i + y) {
            chars[a++] = s.charAt(i);
        }

        return new String(chars);
    }
}
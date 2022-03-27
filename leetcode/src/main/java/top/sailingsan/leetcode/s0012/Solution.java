/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0012;

class Solution {
    int[] numbers = new int[] {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    String[] romans = new String[] {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public String intToRoman(int num) {
        StringBuilder roman = new StringBuilder();

        for (int i = 0; i < numbers.length; i++) {
            while (num >= numbers[i]) {
                num = num - numbers[i];
                roman.append(romans[i]);
            }
            if (num == 0) {
                break;
            }
        }

        return roman.toString();
    }
}
/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0003;

import java.util.HashMap;
import java.util.Map;

/**
 * 滑动窗口
 */
class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s == null) {
            return 0;
        }

        int l = 0;
        int r = 0;
        int max = 0;
        Map<Character, Integer> dict = new HashMap<>();

        while (l < s.length()) {
            while (r < s.length()) {
                if (!dict.containsKey(s.charAt(r))) {
                    dict.put(s.charAt(r), r);
                    r++;
                } else {
                    break;
                }
            }

            max = Math.max(max, r - l);
            dict.remove(s.charAt(l));
            l++;
        }

        return max;
    }
}
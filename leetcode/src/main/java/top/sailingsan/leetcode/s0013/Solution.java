/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0013;

import java.util.HashMap;
import java.util.Map;

class Solution {
    Map<Character, Integer> map = new HashMap<>();

    {
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
    }

    public int romanToInt(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            Integer now = map.get(c);
            if (i < (s.length() - 1) && now < map.get(s.charAt(i + 1))) {
                sum = sum - now;
            } else {
                sum = sum + now;
            }
        }
        return sum;
    }
}

/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        Map<Character, String> dict = new HashMap<>();
        dict.put('2', "abc");
        dict.put('3', "def");
        dict.put('4', "ghi");
        dict.put('5', "jkl");
        dict.put('6', "mno");
        dict.put('7', "pqrs");
        dict.put('8', "tuv");
        dict.put('9', "wxyz");

        result.add("");
        for (int i = 0; i < digits.length(); i++) {
            Character c = digits.charAt(i);
            String letters = dict.get(c);
            int size = result.size();
            for (int k = 0; k < size; k++) {
                String item = result.remove(0);
                for (int j = 0; j < letters.length(); j++) {
                    result.add(item + letters.charAt(j));
                }
            }
        }

        return result;
    }
}

/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0001;

import java.util.HashMap;
import java.util.Map;

/**
 * hash
 */
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> dict = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (dict.containsKey(target - nums[i])) {
                return new int[] {i, dict.get(target - nums[i])};
            } else {
                dict.put(nums[i], i);
            }
        }
        return null;
    }
}


/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0015;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // sort
        Arrays.sort(nums);

        // i j double ponit
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                break;
            }

            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int j = i + 1;
            int k = nums.length - 1;

            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (sum > 0) {
                    k--;
                } else if (sum < 0) {
                    j++;
                } else if (sum == 0) {
                    List<Integer> item = new ArrayList<>();
                    item.add(nums[i]);
                    item.add(nums[j]);
                    item.add(nums[k]);
                    result.add(item);
                    while (j < k) {
                        if (nums[k] == nums[k - 1]) {
                            k--;
                        } else {
                            k--;
                            break;
                        }
                    }
                    while (j < k) {
                        if (nums[j] == nums[j + 1]) {
                            j++;
                        } else {
                            j++;
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }
}

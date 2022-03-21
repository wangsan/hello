/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.leetcode.s0004;

/**
 * 归并排序
 * 分治思想（2分）-
 */
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] nums3 = new int[nums1.length + nums2.length];
        int a = 0;
        int b = 0;
        int c = 0;
        while (a < nums1.length || b < nums2.length) {
            int av = a >= nums1.length ? Integer.MAX_VALUE : nums1[a];
            int bv = b >= nums2.length ? Integer.MAX_VALUE : nums2[b];

            if (av <= bv) {
                nums3[c] = av;
                a++;
            } else {
                nums3[c] = bv;
                b++;
            }

            c++;
        }

        int x = nums3[nums3.length / 2];
        int y = nums3[(nums3.length - 1) / 2];
        return (double) (x + y) / 2;
    }
}
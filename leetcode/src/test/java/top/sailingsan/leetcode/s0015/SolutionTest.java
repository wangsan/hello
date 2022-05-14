package top.sailingsan.leetcode.s0015;/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */

import java.sql.SQLOutput;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * created by wangsan on 2022/03/19.
 *
 * @author wangsan
 */
class SolutionTest {
    Solution solution = new Solution();

    @Test
    public void test() {
     solution.threeSum(new int[]{-1,0,1,2,-1,-4}).forEach(System.out::println);
    }

}

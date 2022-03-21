package top.sailingsan.leetcode.s0005;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(solution.longestPalindrome("babad"), "bab");
        assertEquals(solution.longestPalindrome("cbbd"), "bb");
    }

}
package top.sailingsan.leetcode.s0005;

/**
 * 中心扩散，
 * 动态规划-
 * 马拉车-
 */
class Solution {
    public String longestPalindrome(String s) {
        int maxLength = 0;
        int maxLeft = 0;

        for (int i = 0; i < s.length(); i++) {
            int l = i;
            int r = i;
            int m = 1;
            while (l - 1 >= 0 && s.charAt(l - 1) == s.charAt(r)) {
                l--;
                m++;
            }
            while (r + 1 < s.length() && s.charAt(l) == s.charAt(r + 1)) {
                r++;
                m++;
            }
            while (l - 1 >= 0 && r + 1 < s.length() && s.charAt(l - 1) == s.charAt(r + 1)) {
                m += 2;
                l--;
                r++;
            }

            if (maxLength < m) {
                maxLength = m;
                maxLeft = l;
            }
        }

        return s.substring(maxLeft, maxLeft + maxLength);
    }
}
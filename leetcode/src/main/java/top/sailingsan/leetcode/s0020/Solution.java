package top.sailingsan.leetcode.s0020;

import java.util.Stack;

class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '{' || c == '[' || c == '(') {
                stack.push(c);
            } else {
                if (stack.empty()) {
                    return false;
                }
                Character pop = stack.pop();
                if ((pop.equals('{') && c != '}') || (pop.equals('[') && c != ']') || (pop.equals('(') && c != ')')) {
                    return false;
                }
            }
        }

        return stack.empty();
    }
}

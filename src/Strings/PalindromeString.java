package Strings;

/**
 * Created by OlehKa on 27.07.2016.
 */
public class PalindromeString {

    // :( Slow
    public int isPalindrome1(String a) {
        a = a.toLowerCase();
        for (char c : a.toCharArray()) {
            if (c < '0' || (c > '9' && c < 'a') || c > 'z') {
                a = a.replace(String.valueOf(c), "");
            }
        }
        char[] chars = a.toCharArray();
        if (chars.length == 0) return 1;
        int n = chars.length - 1;
        for (int i = 0; i <= n / 2; i++) {
            if (chars[i] != chars[n - i]) {
                return 0;
            }
        }
        return 1;
    }

    // Faster :)
    public int isPalindrome2(String a) {
        char[] chars = a.toLowerCase().toCharArray();
        int left = getAlphaNumericPosition(chars, 0, chars.length - 1, false);
        if (left == -1) return 1;
        int right = getAlphaNumericPosition(chars, left + 1, chars.length - 1, true);
        if (right == -1) return 1;
        while (left < right) {
            if (chars[left] != chars[right]) return 0;
            left = getAlphaNumericPosition(chars, left + 1, right, false);
            if (left == -1) return 1;
            right = getAlphaNumericPosition(chars, left, right - 1, true);
            if (right == -1) return 1;
        }
        return 1;
    }

    int getAlphaNumericPosition(char[] chars, int start, int end, boolean reverse) {
        if (reverse) {
            for (int i = end; i >= start; i--) {
                if ((chars[i] >= '0' && chars[i] <= '9') || (chars[i] >= 'a' && chars[i] <= 'z')) return i;
            }
        } else {
            for (int i = start; i <= end; i++) {
                if ((chars[i] >= '0' && chars[i] <= '9') || (chars[i] >= 'a' && chars[i] <= 'z')) return i;
            }
        }

        return -1;
    }

    // Correct Solution :D
    public int isPalindrome(String a) {
        StringBuilder sb = new StringBuilder();
        for (char c : a.toLowerCase().toCharArray()) {
            if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z')) sb.append(c);
        }
        int n = sb.length() - 1;
        if (n == -1) return 1;
        for (int i = 0; i <= n / 2; i++) {
            if (sb.charAt(i) != sb.charAt(n - i)) return 0;
        }
        return 1;
    }

    public static void main(String[] args) {
        PalindromeString instance = new PalindromeString();
        System.out.println(instance.isPalindrome("race a car"));
        System.out.println(instance.isPalindrome("2z2"));
    }
}

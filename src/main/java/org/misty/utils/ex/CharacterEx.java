package org.misty.utils.ex;

public class CharacterEx {

    /**
     * Calculate how many Chinese characters there are
     */
    public static int countChinese(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c >= '\u4e00' && c <= '\u9fa5') {
                count++;
            }
        }
        return count;
    }

}

package TextEdit;

public interface TextProcessing {

    /**
     *  find the difference (a single substring) between two strings
     * */
    default public String findDifference(String prev, String next) {

        // set the shortest string as s1, the longest string as s2
        String s1 = prev, s2 = next;
        if (prev.length() > next.length()) {
            s1 = next;
            s2 = prev;
        }

        // calculate the start and end indexes of the difference between s1 and s2
        int start = -1, end = -1;
        if (s2.startsWith(s1)) {
            // if a single difference (a single substring) exists in s2's right extreme
            start = s1.length();
            end = s2.length();
        } else if (s2.endsWith(s1)) {
            // if a single difference (a single substring) exists in s2's left extreme
            start = 0;
            end = s2.length() - s1.length();
        } else {
            // if a single difference (a single substring) exists in s2's middle
            for (int c = 0; c < s2.length(); c++) {
                try {
                    if ( start < 0 & prev.charAt(c) != next.charAt(c) ) start = c;
                } catch (StringIndexOutOfBoundsException e) {
                    end = c;
                }
            }
        }

        return s2.substring(start, end);
    }

}

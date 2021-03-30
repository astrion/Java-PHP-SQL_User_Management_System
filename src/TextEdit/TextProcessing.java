package TextEdit;

public interface TextProcessing {

    default public String findChange(String prev, String next) {

        String s1 = prev, s2 = next;
        int start = -1, end = -1;

        if (prev.length() > next.length()) {
            s1 = next;
            s2 = prev;
        }

        if (s2.startsWith(s1)) {
            // added to the right extreme
            start = s1.length();
            end = s2.length();
        } else if (s2.endsWith(s1)) {
            // added to the left extreme
            start = 0;
            end = s2.length() - s1.length();
        } else {
            // middle
            for (int c = 0; c < s2.length(); c++) {
                try {
                    if ( start < 0 & prev.charAt(c) != next.charAt(c) ) start = c;
                } catch (StringIndexOutOfBoundsException e) {
                    end = c;
                }
            }
        }

        String diff = "";
        diff = s2.substring(start, end);

        return diff;
    }

}

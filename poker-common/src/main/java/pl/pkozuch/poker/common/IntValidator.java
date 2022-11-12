package pl.pkozuch.poker.common;

public class IntValidator {

    public static boolean isInt(String s) {
        if (s == null) return false;
        return s.matches("^\\d+$");
    }

    public static boolean isInt(String[] s) {
        boolean isInt = true;
        for (String a : s) {
            if (!isInt(a)) {
                isInt = false;
                break;
            }
        }

        return isInt;
    }
}

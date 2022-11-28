package pl.pkozuch.poker.common;

/**
 * Utility class to validate if string literal can be converted to <b>POSITIVE</b> integer.
 */
public class IntValidator {

    private IntValidator() {
    }

    /**
     * Checks if string literal can be converted to integer. Works only for positive literals.
     *
     * @param s literal to be checked
     * @return true if {@code s} can be converted to positive integer. False otherwise.
     */
    public static boolean isInt(String s) {
        if (s == null) return false;
        return s.matches("^\\d+$");
    }

    /**
     * Checks if EVERY string literal in array can be converted to integer.
     *
     * @param s array of literals to be checked
     * @return true if all literals from {@code s} can be converted to positive integers. False otherwise.
     */
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

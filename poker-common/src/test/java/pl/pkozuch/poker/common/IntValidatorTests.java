package pl.pkozuch.poker.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IntValidatorTests {

    @Test
    public void testIsInt() {
        String a = "123";
        String b = "123.45";
        String c = "123f4";
        String d = "0";

        Assertions.assertTrue(IntValidator.isInt(a));
        Assertions.assertFalse(IntValidator.isInt(b));
        Assertions.assertFalse(IntValidator.isInt(c));
        Assertions.assertTrue(IntValidator.isInt(d));
    }

    @Test
    public void testIsInt__Array() {
        String[] validNumbers = new String[2];
        String[] invalidNumbers__float = new String[2];
        String[] invalidNumbers__NAN = new String[2];

        validNumbers[0] = "0";
        validNumbers[1] = "123";

        invalidNumbers__float[0] = "123";
        invalidNumbers__float[1] = "123.45";

        invalidNumbers__NAN[0] = "123";
        invalidNumbers__NAN[1] = "123f4";

        Assertions.assertTrue(IntValidator.isInt(validNumbers));
        Assertions.assertFalse(IntValidator.isInt(invalidNumbers__float));
        Assertions.assertFalse(IntValidator.isInt(invalidNumbers__NAN));
    }
}

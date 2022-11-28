package pl.pkozuch.poker.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class IntValidatorTests {

    @ParameterizedTest
    @ValueSource(strings = {"123", "0"})
    void testIsInt__Successful(String v) {
        Assertions.assertTrue(IntValidator.isInt(v));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123.45", "123f4", "-1"})
    void testIsInt__Unsuccessful(String v) {
        Assertions.assertFalse(IntValidator.isInt(v));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testIsInt__Null() {
        String s = null;
        Assertions.assertFalse(IntValidator.isInt(s));
    }

    @Test
    void testIsInt__Array() {
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

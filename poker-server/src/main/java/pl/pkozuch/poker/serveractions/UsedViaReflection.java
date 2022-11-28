package pl.pkozuch.poker.serveractions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Marks method/variable which is invoked by reflection
 */
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface UsedViaReflection {
}

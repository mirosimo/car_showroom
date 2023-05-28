package mirosimo.car_showroom2.custom_annotations;



import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import mirosimo.car_showroom2.custom_validators.EnChars1_20Validator;


@Documented
@Constraint(validatedBy = EnChars1_20Validator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnChars1_20 {
	String message() default "Chars without diacritic - length 1-20";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

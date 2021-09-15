package guide.springboot.sample.lang.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

@Documented
@Target(ElementType.PARAMETER)
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$")
public @interface Uuid {
    String message() default "must be UUID";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package guru.qa.niffler.jupiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.channels.Selector;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface User {
    Selector selector() default Selector.SIMPLE_USER;;

    enum Selector{
        INVITATION_SEND, INVITATION_RECIEVED, WITH_FRIENDS, SIMPLE_USER
    }
}

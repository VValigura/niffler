package guru.qa.niffler.jupiter.annotation.meta;

import guru.qa.niffler.jupiter.extension.DBCategoryExtension;
import guru.qa.niffler.jupiter.extension.DBSpendExtension;
import guru.qa.niffler.jupiter.extension.DBCreateUserExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith({DBCreateUserExtension.class,
        DBCategoryExtension.class,
        DBSpendExtension.class
})
public @interface DBTest {
}

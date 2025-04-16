package guru.qa.niffler.jupiter.annotation;


import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.jupiter.extension.CategoriesExtension;
import guru.qa.niffler.jupiter.extension.SpendExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith({CategoriesExtension.class, SpendExtension.class, BrowserExtension.class})
public @interface WebTest {
}

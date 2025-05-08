package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ParameterResolver;

public abstract class CreateUserExtension implements BeforeEachCallback, ParameterResolver {

    abstract UserJson createUser(UserJson user);
}

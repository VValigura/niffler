package guru.qa.niffler.jupiter.extension;

import com.github.javafaker.Faker;
import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserDataEntity;
import guru.qa.niffler.data.repository.UserRepository;
import guru.qa.niffler.data.repository.UserRepositoryHibernate;
import guru.qa.niffler.data.repository.UserRepositorySpring;
import guru.qa.niffler.jupiter.annotation.TestUser;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.platform.commons.support.AnnotationSupport;

public class HibernateCreateUserExtension extends CreateUserExtension {
    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(HibernateCreateUserExtension.class);

    UserRepository userRepository = new UserRepositoryHibernate();


    @Override
    UserJson createUser(UserJson user) {
        userRepository.createUserInAuth(UserAuthEntity.fromJson(user));
        userRepository.createUserInUserData(UserDataEntity.fromJson(user));
        return user;
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                TestUser.class
        ).ifPresent(cat -> {
            Faker faker = new Faker();
            UserJson userJson = UserJson.createUser(faker.name().fullName(), faker.internet().password());
            userJson = createUser(userJson);
            extensionContext.getStore(NAMESPACE).put("testUser", userJson);
        });
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get("testUser");
    }
}

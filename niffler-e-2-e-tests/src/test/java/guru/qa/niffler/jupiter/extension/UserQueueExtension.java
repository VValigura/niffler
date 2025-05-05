package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UserQueueExtension implements BeforeEachCallback,
        AfterEachCallback,
        ParameterResolver {

    private static final Queue<UserJson> INVITATION_SEND = new ConcurrentLinkedQueue<>();
    private static final Queue<UserJson> INVITATION_RECIEVED = new ConcurrentLinkedQueue<>();
    private static final Queue<UserJson> WITH_FRIENDS = new ConcurrentLinkedQueue<>();
    private static final Queue<UserJson> SIMPLE_USER = new ConcurrentLinkedQueue<>();

    static {
        INVITATION_SEND.add(UserJson.createUser("INVITATION_SEND1", "SEND1"));
        INVITATION_SEND.add(UserJson.createUser("INVITATION_SEND2", "SEND2"));

        INVITATION_RECIEVED.add(UserJson.createUser("INVITATION_RECIEVED1", "RECIEVED1"));
        INVITATION_RECIEVED.add(UserJson.createUser("INVITATION_RECIEVED2", "RECIEVED2"));

        WITH_FRIENDS.add(UserJson.createUser("WITH_FRIENDS1", "FRIENDS1"));
        WITH_FRIENDS.add(UserJson.createUser("WITH_FRIENDS2", "FRIENDS2"));

        SIMPLE_USER.add(UserJson.createUser("SIMPLE_USER1", "USER1"));
        SIMPLE_USER.add(UserJson.createUser("SIMPLE_USER2", "USER2"));
    }

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(UserQueueExtension.class);

    @Override
    public void beforeEach(ExtensionContext extensionContext) {

        UserJson userJson = null;
        while (userJson == null) {
            List<User> users = Arrays.stream(extensionContext.getRequiredTestMethod().getParameters()).filter(p -> AnnotationSupport.isAnnotated(p, User.class)).map(p -> p.getAnnotation(User.class)).toList();
            switch (users.get(0).selector()) {
                case SIMPLE_USER: userJson = SIMPLE_USER.poll();
                    break;
                case WITH_FRIENDS: userJson = WITH_FRIENDS.poll();
                    break;
                case INVITATION_SEND: userJson = INVITATION_SEND.poll();
                    break;
                case INVITATION_RECIEVED: userJson = INVITATION_RECIEVED.poll();
                    break;
            }
        }
        Allure.getLifecycle().updateTestCase(testCase -> {
            testCase.setStart(new Date().getTime());
        });

        extensionContext.getStore(NAMESPACE).put(extensionContext.getUniqueId(), userJson);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(UserJson.class);

    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), UserJson.class);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext){
        UserJson userJson = extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), UserJson.class);
        List<User> users = Arrays.stream(extensionContext.getRequiredTestMethod().getParameters()).filter(p -> AnnotationSupport.isAnnotated(p, User.class)).map(p -> p.getAnnotation(User.class)).toList();
        switch (users.get(0).selector()) {
            case SIMPLE_USER: SIMPLE_USER.add(userJson);
            case WITH_FRIENDS: WITH_FRIENDS.add(userJson);
            case INVITATION_SEND: INVITATION_SEND.add(userJson);
            case INVITATION_RECIEVED: INVITATION_RECIEVED.add(userJson);
        }
    }
}


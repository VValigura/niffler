package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.AuthApiClient;
import guru.qa.niffler.api.cookie.ThreatSafeCookieStore;
import guru.qa.niffler.jupiter.annotation.ApiLogin;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

public class ApiLoginExtension implements BeforeEachCallback, AfterEachCallback {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(ApiLoginExtension.class);

    private final AuthApiClient authApiClient = new AuthApiClient();

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        AnnotationSupport.findAnnotation(
                context.getRequiredTestMethod(),
                ApiLogin.class
        ).ifPresent( user -> {
            authApiClient.doLogin(user.username(), user.password());
    });

    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        ThreatSafeCookieStore.INSTANCE.clearCookies();

    }

    public static void setToken(String token) {
        ContextExtension.context().getStore(NAMESPACE).put("token", token);
    }

    public static String getToken() {
        return ContextExtension.context().getStore(NAMESPACE).get("token", String.class);
    }

    public static void setCodeVerifier(String codeVerifier) {
        ContextExtension.context().getStore(NAMESPACE).put("codeVerifier", codeVerifier);
    }

    public static String getCodeVerifier() {
        return ContextExtension.context().getStore(NAMESPACE).get("codeVerifier", String.class);
    }

    public static void setCodeChallange(String codeChallange) {
        ContextExtension.context().getStore(NAMESPACE).put("codeChallange", codeChallange);
    }

    public static String getCodeChallange() {
        return ContextExtension.context().getStore(NAMESPACE).get("codeChallange", String.class);
    }

    public static void setCode(String code) {
        ContextExtension.context().getStore(NAMESPACE).put("code", code);
    }

    public static String getCode() {
        return ContextExtension.context().getStore(NAMESPACE).get("code", String.class);
    }


}

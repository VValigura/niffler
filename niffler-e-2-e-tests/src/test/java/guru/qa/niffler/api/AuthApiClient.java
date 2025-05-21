package guru.qa.niffler.api;

import guru.qa.niffler.api.cookie.ThreatSafeCookieStore;
import guru.qa.niffler.api.interceptor.CodeInterceptor;
import guru.qa.niffler.jupiter.extension.ApiLoginExtension;
import guru.qa.niffler.model.oauth.TokenJson;
import guru.qa.niffler.utils.OAuthUtils;
import lombok.SneakyThrows;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.jupiter.api.extension.ExtensionContext;
import retrofit2.Call;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class AuthApiClient extends ApiClient {

    private final AuthApi authApi ;

    public AuthApiClient() {
        super(CFG.authUrl(),
                true,
                JacksonConverterFactory.create(),
                HttpLoggingInterceptor.Level.BODY,
                new CodeInterceptor()
                );

        this.authApi = retrofit.create(AuthApi.class);
    }

    @SneakyThrows
    public void doLogin(String login, String password) {
        String codeVerifier = OAuthUtils.generateCodeVerifier();
        String codeChallenge = OAuthUtils.generateCodeChallange(codeVerifier);


        authApi.preRequest("code",
                "client",
                "openid",
                CFG.frontUrl() + "/authorized",
                codeChallenge,
                "S256").execute();

        authApi.login(ThreatSafeCookieStore.INSTANCE.getCookieValue("XSRF-TOKEN"),
                login,
                password).execute();

        TokenJson body = authApi.token(ApiLoginExtension.getCode(),
                CFG.frontUrl() + "/authorized",
                codeVerifier,
                "authorization_code",
                "client").execute().body();

        ApiLoginExtension.setToken(body.idToken());




    }
}

package guru.qa.niffler.api;

import guru.qa.niffler.api.cookie.ThreatSafeCookieStore;
import guru.qa.niffler.config.Config;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.net.CookieManager;
import java.net.CookiePolicy;

public abstract class ApiClient {

    protected static final Config CFG = Config.getInstance();

    protected final OkHttpClient okHttpClient;
    protected final Retrofit retrofit;

    public ApiClient(String baseUrl) {
        this(baseUrl, false, JacksonConverterFactory.create(), Level.BODY);
    }

    public ApiClient(String baseUrl, Level logLevel) {
        this(baseUrl, false, JacksonConverterFactory.create(), logLevel);
    }

    public ApiClient(String baseUrl, Converter.Factory converter, Level logLevel) {
        this(baseUrl, false, converter, logLevel);
    }

    public ApiClient(String baseUrl, Converter.Factory converter) {
        this(baseUrl, false, converter, Level.BODY);
    }

    public ApiClient(String baseUrl, boolean followRedirect, Level logLevel) {
        this(baseUrl, followRedirect, JacksonConverterFactory.create(), logLevel);
    }

    public ApiClient(String baseUrl, boolean followRedirect) {
        this(baseUrl, followRedirect, JacksonConverterFactory.create(), Level.BODY);
    }

    public ApiClient(String baseUrl,
                     boolean followRedirect,
                     Converter.Factory converter,
                     Level logLevel,
                     Interceptor... interceptor) {
        OkHttpClient.Builder okHttpClientBuilder= new OkHttpClient.Builder();
        if(interceptor != null) {
            for (Interceptor i : interceptor) {
                okHttpClientBuilder.addNetworkInterceptor(i);
            }
        }

        okHttpClientBuilder.cookieJar(
                new JavaNetCookieJar(
                        new CookieManager(ThreatSafeCookieStore.INSTANCE, CookiePolicy.ACCEPT_ALL)));


        this.okHttpClient = okHttpClientBuilder.addNetworkInterceptor(
                new HttpLoggingInterceptor()
                        .setLevel(logLevel)
                            ).followRedirects(followRedirect)
                                    .build();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converter)
                .client(okHttpClient)
                .build();
    }
}

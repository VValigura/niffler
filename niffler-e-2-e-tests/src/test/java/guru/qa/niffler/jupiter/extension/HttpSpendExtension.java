package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.SpendApi;
import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.jupiter.annotation.Spend;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Date;

public class HttpSpendExtension extends AbstractSpendExtension {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(HttpSpendExtension.class);

    private final SpendApiClient spendApiClient = new SpendApiClient();



//    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
//            .build();



//    private final Retrofit retrofit = new Retrofit.Builder()
//            .client(okHttpClient)
//            .baseUrl("http://127.0.0.1:8093/")
//            .addConverterFactory(JacksonConverterFactory.create())
//            .build();


    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {


        CategoryJson category = extensionContext.getStore(HttpCategoryExtension.NAMESPACE).get("category", CategoryJson.class);

        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                Spend.class
        ).ifPresent(spend -> {
            SpendJson spendJson = new SpendJson(
                    null,
                    new Date(),
                    category.category(),
                    spend.currency(),
                    spend.amount(),
                    spend.description(),
                    category.username()
            );

            SpendJson result = createSpend(spendJson);
            extensionContext.getStore(NAMESPACE).put("spend", result);


        });
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(SpendJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get("spend");
    }

    @Override
    @SneakyThrows
    protected SpendJson createSpend(SpendJson spendJson) {
        return spendApiClient.createSpend(spendJson);
//        SpendApi spendApi = retrofit.create(SpendApi.class);
//
//        try {
//            return spendApi.createSpend(spendJson).execute().body();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }


    @Override
    public void afterEach(ExtensionContext context) throws Exception {

    }
}

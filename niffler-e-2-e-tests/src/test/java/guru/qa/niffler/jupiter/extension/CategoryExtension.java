package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.SpendApi;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.model.CategoryJson;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class CategoryExtension implements BeforeEachCallback {
    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .build();

    private final Retrofit retrofit = new Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://127.0.0.1:8093/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();


    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        SpendApi spendApi = retrofit.create(SpendApi.class);

        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                Category.class
        ).ifPresent(category -> {
            CategoryJson categoryJson = new CategoryJson(
                    null,
                    category.description(),
                    category.username()
            );
            try {
                CategoryJson result = spendApi.createCategory(categoryJson).execute().body();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}

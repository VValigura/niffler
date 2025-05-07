package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.SpendApi;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.model.CategoryJson;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class HttpCategoryExtension extends AbstractCategoryExtension {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(HttpCategoryExtension.class);

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .build();

    private final Retrofit retrofit = new Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://127.0.0.1:8093/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();


    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {


        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                Category.class
        ).ifPresent(category -> {
            CategoryJson categoryJson = new CategoryJson(
                    null,
                    category.category(),
                    category.username()
            );
            CategoryJson result = createCategory(categoryJson);
            extensionContext.getStore(NAMESPACE).put("category", result);


        });
    }

    @Override
    protected CategoryJson createCategory(CategoryJson categoryJson) {
        SpendApi spendApi = retrofit.create(SpendApi.class);
        try {
            return spendApi.createCategory(categoryJson).execute().body();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void removeCategory(CategoryJson categoryJson) {

    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {

    }
}

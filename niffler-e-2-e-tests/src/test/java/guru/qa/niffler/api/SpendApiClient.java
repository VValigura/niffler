package guru.qa.niffler.api;

import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import retrofit2.Call;

import java.io.IOException;

public class SpendApiClient extends ApiClient {

    private final SpendApi spendApi;

    public SpendApiClient() {
        super(CFG.spendUrl());
        this.spendApi = retrofit.create(SpendApi.class);
    }



    public SpendJson createSpend(SpendJson spend) throws IOException {
        return spendApi.createSpend(spend)
                .execute()
                .body();
    }

    public CategoryJson createCategory(CategoryJson category) throws IOException {
        return spendApi.createCategory(category)
                .execute()
                .body();
    }
}

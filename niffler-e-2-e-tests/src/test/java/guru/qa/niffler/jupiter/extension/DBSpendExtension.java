package guru.qa.niffler.jupiter.extension;


import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.data.repository.SpendRepositoryFactory;
import guru.qa.niffler.data.repository.SpendRepositoryJdbc;
import guru.qa.niffler.jupiter.annotation.Spend;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.Date;

public class DBSpendExtension extends AbstractSpendExtension{

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(DBSpendExtension.class);

    private final SpendRepository spendRepository = SpendRepositoryFactory.getSpendRepository();



    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        CategoryJson category = extensionContext.getStore(DBCategoryExtension.NAMESPACE).get("category", CategoryJson.class);

        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                Spend.class
        ).ifPresent(spend -> {
            SpendEntity spendEntity = new SpendEntity();
            spendEntity.setCategory(category.id().toString());
            spendEntity.setUsername(category.username());
            spendEntity.setCurrency(spend.currency());
            spendEntity.setSpendDate(new Date());
            spendEntity.setAmount(spend.amount());
            spendEntity.setDescription(spend.description());

            SpendJson result = createSpend(SpendJson.fromEntity(spendEntity));

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
    protected SpendJson createSpend(SpendJson spendJson) {
        return SpendJson.fromEntity(spendRepository.createSpend(SpendEntity.fromJson(spendJson)));
    }
}

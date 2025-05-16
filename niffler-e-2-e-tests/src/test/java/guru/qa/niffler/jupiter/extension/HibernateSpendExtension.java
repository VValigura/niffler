package guru.qa.niffler.jupiter.extension;


import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.data.repository.SpendRepositoryHibernate;
import guru.qa.niffler.jupiter.annotation.Spend;
import guru.qa.niffler.jupiter.annotation.Spends;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class HibernateSpendExtension extends AbstractSpendExtension{

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(HibernateSpendExtension.class);

    private final SpendRepository spendRepository = new SpendRepositoryHibernate();



    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        CategoryJson category = extensionContext.getStore(HibernateCategoryExtension.NAMESPACE).get("category", CategoryJson.class);

        List<Spend> allSpends = new ArrayList<>();

        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                Spends.class
        ).ifPresent( spends ->
            allSpends.addAll(Arrays.stream(spends.value()).toList())
        );

        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                Spend.class
        ).ifPresent(allSpends::add);

        if (!allSpends.isEmpty()){

            List<SpendJson> spendJsonList = new ArrayList<>();
            for (Spend spend : allSpends) {
                CategoryEntity categoryEntity = new CategoryEntity();
                categoryEntity.setId(category.id());

                SpendEntity spendEntity = new SpendEntity();
                spendEntity.setCategory(categoryEntity);
                spendEntity.setUsername(category.username());
                spendEntity.setCurrency(spend.currency());
                spendEntity.setSpendDate(new Date());
                spendEntity.setAmount(spend.amount());
                spendEntity.setDescription(spend.description());

                spendJsonList.add(createSpend(SpendJson.fromEntity(spendEntity)));
            }

            extensionContext.getStore(NAMESPACE).put(extensionContext.getUniqueId(), spendJsonList);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter().getType();
        return type.isAssignableFrom(SpendJson.class) || type.isAssignableFrom(SpendJson[].class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter().getType();

        List<SpendJson> spendJsonList = extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), List.class);

        return type.isAssignableFrom(SpendJson.class) ? spendJsonList.getFirst() : spendJsonList.toArray(SpendJson[]::new);
    }


    @Override
    protected SpendJson createSpend(SpendJson spendJson) {
        return SpendJson.fromEntity(spendRepository.createSpend(SpendEntity.fromJson(spendJson)));
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        List<SpendJson> spendJsonList = context.getStore(NAMESPACE).get(context.getUniqueId(), List.class);

        if (spendJsonList != null) {
            for (SpendJson spendJson : spendJsonList) {
                spendRepository.deleteSpend(SpendEntity.fromJson(spendJson));
            }
        }
    }
}

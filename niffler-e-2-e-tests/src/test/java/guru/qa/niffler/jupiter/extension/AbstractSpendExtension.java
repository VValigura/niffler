package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.data.repository.SpendRepositoryFactory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.Date;

public abstract class AbstractSpendExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(SpendExtension.class);

    private final SpendRepository spendRepository = SpendRepositoryFactory.spendRepository();


    @Override
    public void beforeEach(ExtensionContext extensionContext){
        CategoryJson categoryJson = extensionContext.getStore(CategoriesExtension.NAMESPACE).get("category", CategoryJson.class);
        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                GenerateSpend.class
        ).ifPresent(
                spend -> {
                    SpendEntity spendEntity = new SpendEntity();
                    spendEntity.setSpendDate(new Date());
                    spendEntity.setCategory(CategoryEntity.fromJSON(categoryJson));
                    spendEntity.setCurrency(spend.currency());
                    spendEntity.setAmount(spend.amount());
                    spendEntity.setDescription(spend.description());
                    spendEntity.setUsername(categoryJson.username());

                    spendEntity = spendRepository.createSpend(spendEntity);


                    extensionContext.getStore(NAMESPACE).put("spend", SpendJson.fromEntity(spendEntity));

                }
        );
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(SpendJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get("spend");
    }

    protected abstract SpendJson createSpend(SpendJson spend);
    protected abstract void removeCategory(SpendJson spend);
}

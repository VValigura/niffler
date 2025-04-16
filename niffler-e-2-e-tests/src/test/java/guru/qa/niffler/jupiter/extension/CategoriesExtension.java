package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.data.repository.SpendRepositoryFactory;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.model.CategoryJson;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

public class CategoriesExtension implements BeforeEachCallback, AfterEachCallback {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(CategoriesExtension.class);

    private final SpendRepository spendRepository = SpendRepositoryFactory.spendRepository();

    @Override
    public void beforeEach(ExtensionContext extensionContext){
        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                GenerateCategory.class
        ).ifPresent(
                category -> {
                        CategoryEntity categoryEntity = new CategoryEntity();
                        categoryEntity.setCategory(category.category());
                        categoryEntity.setUsername(category.username());

                        categoryEntity = spendRepository.createCategory(categoryEntity);

                        extensionContext.getStore(NAMESPACE).put("category", CategoryJson.fromEntity(categoryEntity));
                }
        );
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        CategoryJson categoryJson = extensionContext.getStore(NAMESPACE).get("category", CategoryJson.class);
        spendRepository.removeCategory(CategoryEntity.fromJSON(categoryJson));
    }
}

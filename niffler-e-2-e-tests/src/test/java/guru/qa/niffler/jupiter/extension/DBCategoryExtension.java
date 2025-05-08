package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.data.repository.SpendRepositoryFactory;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

public class DBCategoryExtension extends AbstractCategoryExtension {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(DBCategoryExtension.class);

    private final SpendRepository spendRepository = SpendRepositoryFactory.getSpendRepository();


    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        UserJson testUser = extensionContext.getStore(DBCreateUserExtension.NAMESPACE).get("testUser", UserJson.class);

        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                Category.class
        ).ifPresent(cat -> {

            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setCategory(cat.category());
            categoryEntity.setUsername(testUser.username());

            CategoryJson categoryJson = createCategory(CategoryJson.fromEntity(categoryEntity));

            extensionContext.getStore(NAMESPACE).put("category", categoryJson);

        });
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        CategoryJson category = extensionContext.getStore(NAMESPACE).get("category", CategoryJson.class);
        removeCategory(category);

    }

    @Override
    protected CategoryJson createCategory(CategoryJson categoryJson) {
        return CategoryJson.fromEntity(spendRepository.createCategory(CategoryEntity.fromJson(categoryJson)));
    }

    @Override
    protected void removeCategory(CategoryJson categoryJson) {
        if (categoryJson != null) {
            spendRepository.deleteCategory(CategoryEntity.fromJson(categoryJson));
        }
    }
}

package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ParameterResolver;

public abstract class AbstractCategoryExtension implements BeforeEachCallback, AfterEachCallback {
    protected abstract CategoryJson createCategory(CategoryJson categoryJson);
    protected abstract void removeCategory(CategoryJson categoryJson);
}

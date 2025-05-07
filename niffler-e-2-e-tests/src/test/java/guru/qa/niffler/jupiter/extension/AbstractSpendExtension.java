package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.*;

public abstract class AbstractSpendExtension implements BeforeEachCallback, ParameterResolver {
    protected abstract SpendJson createSpend(SpendJson spendJson);
}

package guru.qa.niffler.jupiter.extension;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public interface SuiteExtension extends AfterAllCallback {
    @Override
    default void afterAll(ExtensionContext context) throws Exception{
        context.getStore(ExtensionContext.Namespace.GLOBAL)
                .getOrComputeIfAbsent(this.getClass(), k -> {
                    beforeSuite(context);
                    return new ExtensionContext.Store.CloseableResource() {
                        @Override
                        public void close() throws Throwable {
                             afterSuite();
                        }
                    };
                });


    }

    default void beforeSuite(ExtensionContext context){

    };

    default void afterSuite() throws Exception{

    };
}

package guru.qa.niffler.jupiter.extension;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ContextExtension implements BeforeEachCallback, AfterEachCallback {
    @Override
    public void afterEach(ExtensionContext context) throws Exception {

        Holder.INSTANCE.remove();

    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Holder.INSTANCE.set(context);
    }

    public static ExtensionContext context(){
        return Holder.INSTANCE.get();
    }

    private enum Holder{
        INSTANCE;

        ThreadLocal<ExtensionContext> context = new ThreadLocal<>();

        void set(ExtensionContext context){
            this.context.set(context);
        }

        ExtensionContext get(){
            return context.get();
        }

        void remove(){
            context.remove();
        }

    }
}

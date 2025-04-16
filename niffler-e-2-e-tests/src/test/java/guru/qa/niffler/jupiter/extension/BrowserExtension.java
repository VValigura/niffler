package guru.qa.niffler.jupiter.extension;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.LifecycleMethodExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.OutputType;

import java.io.ByteArrayInputStream;
import java.util.Objects;

public class BrowserExtension implements TestExecutionExceptionHandler,
        LifecycleMethodExecutionExceptionHandler
        {


    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
        doScreenshot();
        throw throwable;
    }

    private void doScreenshot(){
        if (WebDriverRunner.hasWebDriverStarted()){
            Allure.addAttachment(
                    "Screenshot",
                    new ByteArrayInputStream(
                            Objects.requireNonNull(
                                    Selenide.screenshot(
                                            OutputType.BYTES))));
        }
    }

            @Override
            public void handleBeforeEachMethodExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
                doScreenshot();
                throw throwable;

    }

            @Override
            public void handleAfterEachMethodExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
                doScreenshot();
                throw throwable;
            }


}

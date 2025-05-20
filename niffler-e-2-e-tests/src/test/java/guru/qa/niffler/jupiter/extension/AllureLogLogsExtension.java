package guru.qa.niffler.jupiter.extension;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class AllureLogLogsExtension implements SuiteExtension{
    @Override
    public void beforeSuite(ExtensionContext context) {
        System.out.println("AllureLogLogsExtension beforeSuite");
    }

    @SneakyThrows
    @Override
    public void afterSuite() throws Exception {
        String caseID = UUID.randomUUID().toString();
        AllureLifecycle lifecycle = Allure.getLifecycle();
        lifecycle.scheduleTestCase(new TestResult().setUuid(caseID).setName("Logs").setStatus(Status.PASSED));
        lifecycle.startTestCase(caseID);
        lifecycle.addAttachment("auth Log", "text/html", ".log", Files.newInputStream(Path.of("./auth.log")));
        lifecycle.addAttachment("auth Log", "text/html", ".log", Files.newInputStream(Path.of("./currency.log")));
        lifecycle.addAttachment("auth Log", "text/html", ".log", Files.newInputStream(Path.of("./gateway.log")));
        lifecycle.addAttachment("auth Log", "text/html", ".log", Files.newInputStream(Path.of("./spend.log")));
        lifecycle.addAttachment("auth Log", "text/html", ".log", Files.newInputStream(Path.of("./userdata.log")));
        lifecycle.startTestCase(caseID);
        lifecycle.writeTestCase(caseID);

    }
}

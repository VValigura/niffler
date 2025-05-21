package guru.qa.niffler.jupiter.extension;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import guru.qa.niffler.api.cookie.ThreatSafeCookieStore;
import guru.qa.niffler.config.Config;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;

public class BrowserExtension implements BeforeTestExecutionCallback {
    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        if(ApiLoginExtension.getToken() != null) {
            Selenide.open(Config.getInstance().frontUrl());
            Selenide.sessionStorage().setItem("id_token", ApiLoginExtension.getToken());
            WebDriverRunner.getWebDriver().manage().addCookie(
                    new Cookie("JSESSIONID",
                            ThreatSafeCookieStore.INSTANCE.getCookieValue("JSESSIONID"))
            );
        }
    }
}

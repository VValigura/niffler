package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class LoginPage {
    private final SelenideElement usernameInput = $("input[name='username']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement submitBtn = $("button[type='submit']");

    @Step("Set username: {username}")
    public LoginPage setUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }


    @Step("Set password: {password}")
    public LoginPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Step("Click submit Button")
    public MainePage clickSubmitBtn() {
        submitBtn.click();
        return new MainePage();
    }
}

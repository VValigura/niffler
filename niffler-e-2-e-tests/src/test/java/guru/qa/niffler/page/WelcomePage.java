package guru.qa.niffler.page;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.$;

public class WelcomePage {
    private SelenideElement loginBtn = $("a[href*='redirect']"),
    registerBtn = $("a[href*='register']");

    @Step("Open Welcome Page")
    public WelcomePage open(){
        Selenide.open("http://127.0.0.1:3000/");
        return this;
    }

    public WelcomePage open(String url){
        Selenide.open(url);
        return this;
    }


    @Step("Redirect to Register Page")
    public LoginPage clickLoginBtn(){
        loginBtn.click();
        return new LoginPage();
    }

    @Step("Redirect to Register Page")
    public RegisterPage clickRegisterBtn(){
        registerBtn.click();
        return new RegisterPage();
    }


}

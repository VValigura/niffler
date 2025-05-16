package guru.qa.niffler.test;


import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import guru.qa.niffler.jupiter.annotation.TestUser;
import guru.qa.niffler.jupiter.annotation.meta.HibernateTest;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.ProfilePage;
import guru.qa.niffler.page.WelcomePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.parallel.Isolated;

import static com.codeborne.selenide.Selenide.webdriver;

//@Isolated
@Order(1)
@Execution(ExecutionMode.SAME_THREAD)
@HibernateTest
public class MultiThreadingClassTest {

    static {
        Configuration.browserSize = "1920×1024";
    }

    @BeforeEach
    void doLogin(UserJson userJson) {
        new WelcomePage().open()
                .clickLoginBtn()
                .setUsername(userJson.username())
                .setPassword(userJson.testData().password())
                .clickSubmitBtn()
                .clickProfileBtn();
    }



    @TestUser
    @Test
    void userIsCreated(UserJson userJson) {
        new ProfilePage()
                .open()
                .checkUserName(userJson.username());
    }

    @TestUser
    @Test
    void userIsCreated2(UserJson userJson) {
        new ProfilePage()
                .open()
                .checkUserName(userJson.username());
    }

    @TestUser
    @Test
    void userIsCreated3(UserJson userJson) {
        new ProfilePage()
                .open()
                .checkUserName(userJson.username());
    }

    @TestUser
    @Test
    void userIsCreated4(UserJson userJson) {
        new ProfilePage()
                .open()
                .checkUserName(userJson.username());
    }

    @TestUser
    @Test
    void userIsCreated5(UserJson userJson) {
        new ProfilePage()
                .open()
                .checkUserName(userJson.username());
    }
    @TestUser
    @Test

    void userIsCreated6(UserJson userJson) {
        new ProfilePage()
                .open()
                .checkUserName(userJson.username());
    }
    @TestUser
    @Test

    void userIsCreated7(UserJson userJson) {
        new ProfilePage()
                .open()
                .checkUserName(userJson.username());
    }
    @TestUser
    @Test

    void userIsCreated8(UserJson userJson) {
        new ProfilePage()
                .open()
                .checkUserName(userJson.username());
    }

    @TestUser
    @Test
    void userIsCreated9(UserJson userJson) {
        new ProfilePage()
                .open()
                .checkUserName(userJson.username());
    }

    @TestUser
    @Test
    void userIsCreated10(UserJson userJson) {
        new ProfilePage()
                .open()
                .checkUserName(userJson.username());
    }




    @AfterEach
    void closeWebDriver(){
        webdriver().driver().close();
    }



}

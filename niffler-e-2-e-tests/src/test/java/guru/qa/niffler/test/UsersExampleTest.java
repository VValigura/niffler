package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.WebTest;
import guru.qa.niffler.jupiter.extension.UseQueueExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.WelcomePage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@WebTest
@ExtendWith(UseQueueExtension.class)
public class UsersExampleTest {
    @Test
    void doLogin0(@User UserJson user) {
        new WelcomePage().open()
                .clickLoginBtn()
                .setUsername(user.username())
                .setPassword(user.testData().password())
                .clickSubmitBtn();
        $(".header__avatar").shouldBe(visible);
    }
    @Test
    void doLogin1(UserJson user) {
        System.out.println(user.username());
        System.out.println(user.testData().password());
        new WelcomePage().open()
                .clickLoginBtn()
                .setUsername(user.username())
                .setPassword(user.testData().password())
                .clickSubmitBtn();
        $(".header__avatar").shouldBe(visible);
    }
    @Test
    void doLogin2(UserJson user) {
        new WelcomePage().open()
                .clickLoginBtn()
                .setUsername(user.username())
                .setPassword(user.testData().password())
                .clickSubmitBtn();
        $(".header__avatar").shouldBe(visible);
    }
    @Test
    void doLogin3(UserJson user) {
        new WelcomePage().open()
                .clickLoginBtn()
                .setUsername(user.username())
                .setPassword(user.testData().password())
                .clickSubmitBtn();
        $(".header__avatar").shouldBe(visible);
    }
    @Test
    void doLogin4(UserJson user) {
        new WelcomePage().open()
                .clickLoginBtn()
                .setUsername(user.username())
                .setPassword(user.testData().password())
                .clickSubmitBtn();
        $(".header__avatar").shouldBe(visible);
    }
}

package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class ProfilePage {
    SelenideElement userNameField = $("figcaption");

        public ProfilePage checkUserName(String userName) {
        $("figcaption").shouldHave(text(userName));
        return this;
    }

}

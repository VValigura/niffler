package guru.qa.niffler.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class ProfilePage {
        SelenideElement userNameField = $("figcaption");
        SelenideElement submitBtn = $(".button[type='submit']");
        SelenideElement inputNameField = $("input[name='firstname']");

    public ProfilePage open() {
        Selenide.open("http://127.0.0.1:3000/profile");
        return this;
    }

    public ProfilePage checkUserName(String userName) {
        userNameField.getText().equals(userName);
        return this;
    }

    public ProfilePage changeUserName(String newUserName) {
        inputNameField.setValue(newUserName);
        clickSubmitBtn();
        return this;
    }

    public ProfilePage clickSubmitBtn() {
        $(".main-content").scrollIntoView(false);
        submitBtn.click();
        return this;
    }

}

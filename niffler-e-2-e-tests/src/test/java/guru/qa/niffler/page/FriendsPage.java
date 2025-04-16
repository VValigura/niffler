package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class FriendsPage {
        SelenideElement friendsTable = $(".table");
        SelenideElement removeFriendBtn = $("[data-tooltip-id='remove-friend']");


    @Step("Check friends is present in list")
    public boolean checkFriends(){
        friendsTable.shouldBe(visible);
        return removeFriendBtn.exists();
    }
}

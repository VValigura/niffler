package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MainePage {
    private final SelenideElement friendsBtn = $("li[data-tooltip-id='friends']");
    private final SelenideElement peopleBtn = $("li[data-tooltip-id='people']");

    @Step("Click Friends Button")
    public FriendsPage clickFriendsBtn() {
        friendsBtn.click();
        return new FriendsPage();
    }

    @Step("Click All People Button")
    public AllPeoplePage clickPeopleBtn() {
        peopleBtn.click();
        return new AllPeoplePage();
    }


    @Step("Check friends invintation")
    public boolean checkFriendsInvitation(){
        friendsBtn.shouldBe(visible);
        return friendsBtn.$(".header__sign").exists();
    }


}

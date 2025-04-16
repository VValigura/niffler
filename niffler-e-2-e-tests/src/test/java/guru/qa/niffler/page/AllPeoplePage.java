package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class AllPeoplePage {
    SelenideElement friendsTable = $(".table");

    @Step("Check send invitation is present on list")
    public boolean checkInvitation(){
        friendsTable.shouldBe(visible);
        return $(By.xpath("//*[contains(text(),'Pending invitation')]")).exists();
    }
}

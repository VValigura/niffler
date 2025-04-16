package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.WebTest;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.page.WelcomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;


@WebTest
public class SpendingTest  {

    static {
        Configuration.browserSize = "1920×1024";
    }

    @BeforeEach
    void doLogin() {
       new WelcomePage().open()
               .clickLoginBtn()
               .setUsername("Vova25")
                       .setPassword("Vova25")
                               .clickSubmitBtn();
    }

    @GenerateCategory(
            username = "Vova25",
            category = "Good4"

    )
    @GenerateSpend(
            description = "QA.GURU Advanced 5",
            amount = 75000.00,
            currency = CurrencyValues.RUB
    )
    @Test
    void spendingShouldBeDeletedAfterTableAction(SpendJson spendJson) {
        $(".main-content").scrollIntoView(false);

        SelenideElement rowWithSpending = $(".spendings-table tbody")
                .$$("tr")
                .find(text(spendJson.description()));

        rowWithSpending.$$("td").first().click();
        $(".spendings__bulk-actions button").click();

        $(".spendings-table tbody").$$("tr")
                .shouldHave(size(0));
    }





}

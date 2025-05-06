package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.Spend;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.page.WelcomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

@WebTest
public class WebSpendingTest {
    static final String userNameTest = "TestUser9";
    static final String passwordTest = "Gfhfcnfc";
    static final String descriptionTest = "QA.GURU Advanced 5";
    static final double amountTest = 75000.00;
    static final String categoryTest = "Обучение";

    static {
        Configuration.browserSize = "1920×1024";
    }

    @BeforeEach
    void doLogin() {
        new WelcomePage().open()
                .clickLoginBtn()
                .setUsername(userNameTest)
                .setPassword(passwordTest)
                .clickSubmitBtn();
    }

    @Category(
            category = categoryTest,
            username = userNameTest
    )
    @Spend(
            description = descriptionTest,
            amount = amountTest,
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


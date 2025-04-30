package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.Spend;
import guru.qa.niffler.jupiter.extension.CategoryExtension;
import guru.qa.niffler.jupiter.extension.SpendExtension;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.page.WelcomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

@ExtendWith({CategoryExtension.class, SpendExtension.class})
public class SpendingTest {
    static final String userNameTest = "TestUser4";
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
            description = categoryTest,
            username = userNameTest
    )
    @Spend(
            description = descriptionTest,
            amount = amountTest,
            currency = CurrencyValues.RUB,
            username = userNameTest,
            category = categoryTest
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


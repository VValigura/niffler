package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.Spend;
import guru.qa.niffler.jupiter.annotation.TestUser;
import guru.qa.niffler.jupiter.annotation.meta.DBTest;
import guru.qa.niffler.jupiter.annotation.meta.HibernateTest;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.WelcomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;

@HibernateTest
public class HibernateSpendingTest {
    static final String  userNameTest = "jdbcUser5";
//    static final String passwordTest = "Gfhfcnfc";
    static final String descriptionTest = "QA.GURU Advanced 5";
    static final double amountTest = 75000.00;
    static final String categoryTest = "Обучение3";

    static {
        Configuration.browserSize = "1920×1024";
    }

    @BeforeEach
    void doLogin(UserJson userJson) {
        new WelcomePage().open()
                .clickLoginBtn()
                .setUsername(userJson.username())
                .setPassword(userJson.testData().password())
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
    @TestUser
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
        webdriver().driver().close();
    }
}


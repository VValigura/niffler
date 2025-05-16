package guru.qa.niffler.condition;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.WebElementsCondition;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.TextsMismatch;
import com.codeborne.selenide.impl.CollectionSource;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.utils.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class SpendsInTableCondition extends WebElementsCondition {

    private final SpendJson[] expectedSpends;


    public SpendsInTableCondition(SpendJson[] expectedSpends) {
        this.expectedSpends = expectedSpends;
    }

    @Override
    public String toString() {
        return "";
    }

    @Nonnull
    @Override
    public CheckResult check(Driver driver, List<WebElement> elements) {
        if (elements.size() != expectedSpends.length) {
            return new CheckResult(false, "Expected " + expectedSpends.length + " elements, but found " + elements.size());
        }

        for (int i = 0; i < elements.size(); i++) {
            WebElement row = elements.get(i);
            SpendJson expectedSpendForRow = expectedSpends[i];

            List<WebElement> td = row.findElements(By.cssSelector("td"));

            boolean dateResult = td.get(1).getText().contains(
                    DateUtils.formatDate(expectedSpendForRow.spendDate(), "dd MMM yy")
            );

            if(!dateResult){
                return CheckResult.rejected( "Date is not correct",
                        td.get(1).getText()
                );
            }

            boolean amountResult = td.get(2).getText().contains(
                    String.valueOf(expectedSpendForRow.amount().intValue())
            );
            if (!amountResult) {
                return CheckResult.rejected( "Amount is not correct",
                        td.get(2).getText()
                );
            }

            boolean currencyResult = td.get(3).getText().contains(
                    expectedSpendForRow.currency().toString()
            );
            if (!currencyResult) {
                return CheckResult.rejected( "Currency is not correct",
                        td.get(3).getText()
                );
            }

            boolean descriptionResult = td.get(5).getText().contains(
                    expectedSpendForRow.description()
            );
            if (!descriptionResult) {
                return CheckResult.rejected( "Description is not correct",
                        td.get(5).getText()
                );
            }
        }
        return CheckResult.accepted();
    }

    @Override
    public void fail(CollectionSource collection, CheckResult lastCheckResult, @Nullable Exception cause, long timeoutMs) {
        String actualElementTexts = lastCheckResult.getActualValue();

        String message = lastCheckResult.getMessageOrElse(() -> "Spend mismatch");



        throw new SpendMismatchException(message, collection, Arrays.toString(expectedSpends), actualElementTexts, explanation, timeoutMs, cause);

    }
}

package guru.qa.niffler.condition;

import com.codeborne.selenide.WebElementsCondition;
import guru.qa.niffler.model.SpendJson;

public class SendsCondition {
    public static WebElementsCondition spendsInTable(SpendJson... expectedSpends) {
        return new SpendsInTableCondition(expectedSpends);
    }
}

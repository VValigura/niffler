package guru.qa.niffler.page.components;

import com.codeborne.selenide.SelenideElement;

public class ReactCalendar extends BaseComponent<ReactCalendar> {

    public ReactCalendar(SelenideElement self) {
        super(self);
    }

    public ReactCalendar selectDate(String date) {
        getSelf().$("div[role='grid']").$("div[aria-label='" + date + "']").click();
        return this;
    }

    public String getSelectedDate() {
        return getSelf().$("div[role='grid']").$("div[aria-selected='true']").getText();
    }
}

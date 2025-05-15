package guru.qa.niffler.page.components;

import com.codeborne.selenide.SelenideElement;

public class BaseComponent <T extends BaseComponent<?>>{
    private final SelenideElement self;

    public BaseComponent(SelenideElement self) {
        this.self = self;
    }

    public SelenideElement getSelf() {
        return self;
    }
}

package guru.qa.niffler.page;

public class BasePage <T extends BasePage<?>>{


    @SuppressWarnings("unchecked")
    public T open() {
        // open page
        return (T) this;
    }
}

package guru.qa.niffler.test;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;

@WebTest
public class BaseHttpTest {
    protected static final Config CFG = Config.getInstance();
}

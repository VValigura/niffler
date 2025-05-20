package guru.qa.niffler.test;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SimpleTest {

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Before all tests");
    }


    @Test
    public void simpleTest() {
        Allure.addAttachment("example", "example");
    }

    @Test
    public void simpleTest2() {
        Allure.addAttachment("example", "example");
        Assertions.assertTrue(false);

    }
}

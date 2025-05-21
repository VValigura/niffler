package guru.qa.niffler.test;

import guru.qa.niffler.data.entity.*;
import guru.qa.niffler.data.repository.UserRepository;
import guru.qa.niffler.data.repository.UserRepositoryHibernate;
import guru.qa.niffler.data.repository.UserRepositorySpring;
import guru.qa.niffler.jupiter.annotation.ApiLogin;
import guru.qa.niffler.jupiter.annotation.meta.DBTest;
import guru.qa.niffler.page.ProfilePage;
import guru.qa.niffler.page.WelcomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@DBTest
public class ApiLoginTest {


    static final String userNameTest = "Vova1223";
    static final String passwordTest = "Vova1223";


    @ApiLogin(
            username = userNameTest,
            password = passwordTest
    )

    @Test
    void loginTest() {
        new ProfilePage().open()
                .checkUserName(userNameTest);
    }

}

package guru.qa.niffler.test;

import guru.qa.niffler.data.entity.CurrencyValues;
import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserDataEntity;
import guru.qa.niffler.data.repository.UserRepository;
import guru.qa.niffler.data.repository.UserRepositorySpring;
import guru.qa.niffler.jupiter.annotation.meta.DBTest;
import guru.qa.niffler.page.WelcomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@DBTest
public class LoginTest {

    UserRepository userRepository = new UserRepositorySpring();

    static final String userNameTest = "jdbcUser5";
    static final String passwordTest = "Gfhfcnfc";
    UserDataEntity userDataEntity;

    @BeforeEach
    void createUser(){
        UserAuthEntity userAuthEntity = new UserAuthEntity();
        userAuthEntity.setUsername(userNameTest);
        userAuthEntity.setPassword(passwordTest);
        userAuthEntity.setAccountNonExpired(true);
        userAuthEntity.setAccountNonLocked(true);
        userAuthEntity.setCredentialsNonExpired(true);
        userAuthEntity.setEnabled(true);
        userRepository.createUserInAuth(userAuthEntity);

        userDataEntity = new UserDataEntity();
        userDataEntity.setUsername(userNameTest);
        userDataEntity.setCurrency(CurrencyValues.RUB);
        userRepository.createUserInUserData(userDataEntity);

    }

    @Test
    void loginTest() {
        new WelcomePage().open()
                .clickLoginBtn()
                .setUsername(userNameTest)
                .setPassword(passwordTest)
                .clickSubmitBtn()
                .clickProfileBtn()
                .checkUserName(userNameTest);

        userRepository.findUserInUserDataByID(userDataEntity.getId());


    }

}

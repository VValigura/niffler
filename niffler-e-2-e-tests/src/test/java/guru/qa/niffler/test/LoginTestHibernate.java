package guru.qa.niffler.test;

import guru.qa.niffler.data.entity.*;
import guru.qa.niffler.data.repository.UserRepository;
import guru.qa.niffler.data.repository.UserRepositoryHibernate;
import guru.qa.niffler.jupiter.annotation.meta.DBTest;
import guru.qa.niffler.page.WelcomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@DBTest
public class LoginTestHibernate {

    UserRepository userRepository = new UserRepositoryHibernate();

    static final String userNameTest = "jdbcUser13";
    static final String passwordTest = "Gfhfcnfc";
    UserDataEntity userDataEntity;

    @BeforeEach
    void createUser(){

        AuthorityEntity read = new AuthorityEntity();
        read.setAuthority(Authority.read);
        AuthorityEntity write = new AuthorityEntity();
        write.setAuthority(Authority.write);


        UserAuthEntity userAuthEntity = new UserAuthEntity();
        userAuthEntity.setUsername(userNameTest);
        userAuthEntity.setPassword(passwordTest);
        userAuthEntity.setAccountNonExpired(true);
        userAuthEntity.setAccountNonLocked(true);
        userAuthEntity.setCredentialsNonExpired(true);
        userAuthEntity.setEnabled(true);
        userAuthEntity.addAuthorities(
                read, write
        );

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

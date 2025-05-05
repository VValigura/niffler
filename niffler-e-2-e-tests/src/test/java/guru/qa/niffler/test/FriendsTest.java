package guru.qa.niffler.test;


import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.jupiter.extension.UserQueueExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.AllPeoplePage;
import guru.qa.niffler.page.FriendsPage;
import guru.qa.niffler.page.MainePage;
import guru.qa.niffler.page.WelcomePage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.webdriver;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@WebTest
@ExtendWith(UserQueueExtension.class)
public class FriendsTest {
    @Test
    void InvitationReseivedTest(@User(selector = User.Selector.INVITATION_RECIEVED) UserJson user) {
        MainePage mainePage = new WelcomePage().open()
                .clickLoginBtn()
                .setUsername(user.username())
                .setPassword(user.testData().password())
                .clickSubmitBtn();
        assertThat(mainePage.checkFriendsInvitation(), is(true));
    }

    @Test
    void WithFriendTest(@User(selector = User.Selector.WITH_FRIENDS) UserJson user) {
        FriendsPage friendsPage = new WelcomePage().open()
                .clickLoginBtn()
                .setUsername(user.username())
                .setPassword(user.testData().password())
                .clickSubmitBtn()
                .clickFriendsBtn();

        assertThat(friendsPage.checkFriends(), is(true));
    }

    @Test
    void InvitationSendTest(@User(selector = User.Selector.INVITATION_SEND) UserJson user) {
        AllPeoplePage allPeoplePage = new WelcomePage().open()
                .clickLoginBtn()
                .setUsername(user.username())
                .setPassword(user.testData().password())
                .clickSubmitBtn()
                .clickPeopleBtn();
        assertThat(allPeoplePage.checkInvitation(), is(true));
    }
}

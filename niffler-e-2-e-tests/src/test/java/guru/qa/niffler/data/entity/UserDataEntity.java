package guru.qa.niffler.data.entity;

import guru.qa.niffler.model.UserJson;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class UserDataEntity implements Serializable {
    private UUID id;
    private String username;
    private CurrencyValues currency;
    private String firstname;
    private String surname;
    private byte[] photo;
    private byte[] photoSmall;

    public static UserDataEntity fromJson(UserJson user) {
        UserDataEntity userData = new UserDataEntity();
        userData.setUsername(user.username());
        userData.setCurrency(CurrencyValues.valueOf(user.currency().name()));
        return userData;
    }
}
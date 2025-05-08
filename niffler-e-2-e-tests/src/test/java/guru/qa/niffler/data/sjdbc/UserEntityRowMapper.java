package guru.qa.niffler.data.sjdbc;

import guru.qa.niffler.data.entity.CurrencyValues;
import guru.qa.niffler.data.entity.UserDataEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserEntityRowMapper implements RowMapper<UserDataEntity> {

    public static final UserEntityRowMapper INSTANCE = new UserEntityRowMapper();

    private UserEntityRowMapper() {
    }

    @Override
    public UserDataEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserDataEntity userDataEntity = new UserDataEntity();
        userDataEntity.setUsername(rs.getString("username"));
        userDataEntity.setCurrency(CurrencyValues.valueOf(rs.getString("currency")));
        return userDataEntity;
    }
}

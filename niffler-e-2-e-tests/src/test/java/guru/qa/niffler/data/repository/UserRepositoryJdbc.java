package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.DataBase;
import guru.qa.niffler.data.entity.Authority;
import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserDataEntity;
import guru.qa.niffler.data.jdbc.DataSourceProvider;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryJdbc implements UserRepository {

    private static final DataSource authDataSource = DataSourceProvider.getDataSource(DataBase.AUTH);
    private static final DataSource userDataSource = DataSourceProvider.getDataSource(DataBase.USERDATA);

    private static final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    public UserAuthEntity createUserInAuth(UserAuthEntity userAuthEntity) {
        try (Connection conn = authDataSource.getConnection()){
            conn.setAutoCommit(false);
            try (PreparedStatement userPS = conn.prepareStatement( "INSERT INTO \"user\" (username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired) VALUES (?,?,?,?,?,? )",
                    PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement authorityPS = conn.prepareStatement("INSERT INTO authority (user_id, authority) VALUES (?,?)")) {
                userPS.setString(1, userAuthEntity.getUsername());
                userPS.setString(2, passwordEncoder.encode(userAuthEntity.getPassword()));
                userPS.setBoolean(3, userAuthEntity.getEnabled());
                userPS.setBoolean(4, userAuthEntity.getAccountNonExpired());
                userPS.setBoolean(5, userAuthEntity.getAccountNonLocked());
                userPS.setBoolean(6, userAuthEntity.getCredentialsNonExpired());
                userPS.executeUpdate();

                UUID userId = null;
                try (ResultSet rs = userPS.getGeneratedKeys()) {
                    if (rs.next()) {
                        userId = UUID.fromString(rs.getString("id"));
                    }
                }
                userAuthEntity.setId(userId);

                for (Authority authority: Authority.values()){
                    authorityPS.setObject(1, userId);
                    authorityPS.setString(2, authority.name());
                    authorityPS.addBatch();
                    authorityPS.clearParameters();
                }
                authorityPS.executeBatch();

                conn.commit();

                return userAuthEntity;
            } catch (Exception e) {
                conn.rollback();
                throw new RuntimeException(e);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDataEntity createUserInUserData(UserDataEntity userDataEntity) {
        try (Connection conn = userDataSource.getConnection();
              PreparedStatement userPS = conn.prepareStatement( "INSERT INTO \"user\" (username, currency, firstname, surname, photo, photo_small) VALUES (?,?,?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                userPS.setString(1, userDataEntity.getUsername());
                userPS.setString(2, userDataEntity.getCurrency().name());
                userPS.setString(3, userDataEntity.getFirstname());
                userPS.setString(4, userDataEntity.getSurname());
                userPS.setBytes(5, userDataEntity.getPhoto());
                userPS.setBytes(6, userDataEntity.getPhotoSmall());
                userPS.executeUpdate();

                UUID userId = null;
                try (ResultSet rs = userPS.getGeneratedKeys()) {
                    if (rs.next()) {
                        userId = UUID.fromString(rs.getString("id"));
                    }
                }
                userDataEntity.setId(userId);
                return userDataEntity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UserDataEntity> findUserInUserDataByID(UUID id) {
        return Optional.empty();
    }
}

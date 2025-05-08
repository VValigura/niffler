package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.DataBase;
import guru.qa.niffler.data.entity.Authority;
import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserDataEntity;
import guru.qa.niffler.data.jdbc.DataSourceProvider;
import guru.qa.niffler.data.sjdbc.UserEntityRowMapper;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class UserRepositorySpring implements UserRepository {

    private static final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();


    private static final TransactionTemplate authTxTemplate = new TransactionTemplate(
            new JdbcTransactionManager(
                    DataSourceProvider.getDataSource(DataBase.AUTH)
            )
    );

    private static final JdbcTemplate authTemplate = new JdbcTemplate(
            DataSourceProvider.getDataSource(DataBase.AUTH)
    );

    private static final JdbcTemplate userDataTemplate = new JdbcTemplate(
            DataSourceProvider.getDataSource(DataBase.USERDATA)
    );


    @Override
    public UserAuthEntity createUserInAuth(UserAuthEntity userAuthEntity) {
        return authTxTemplate.execute(status -> {

            KeyHolder kh = new GeneratedKeyHolder();
            authTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO \"user\" (username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired) VALUES (?,?,?,?,?,? )",
                        PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, userAuthEntity.getUsername());
                ps.setString(2, passwordEncoder.encode(userAuthEntity.getPassword()));
                ps.setBoolean(3, userAuthEntity.getEnabled());
                ps.setBoolean(4, userAuthEntity.getAccountNonExpired());
                ps.setBoolean(5, userAuthEntity.getAccountNonLocked());
                ps.setBoolean(6, userAuthEntity.getCredentialsNonExpired());
                return ps;
            }, kh);
            userAuthEntity.setId((UUID)kh.getKeys().get("id"));

            authTemplate.batchUpdate(
                    "INSERT INTO authority (user_id, authority) VALUES (?,?)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setObject(1, userAuthEntity.getId());
                            ps.setString(2, Authority.values()[i].name());
                        }
                                                @Override
                        public int getBatchSize() {
                            return Authority.values().length;
                        }
                    }
            );
            return userAuthEntity;
        });
    }

    @Override
    public UserDataEntity createUserInUserData(UserDataEntity userDataEntity) {
        KeyHolder kh = new GeneratedKeyHolder();
        userDataTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO \"user\" (username, currency, firstname, surname, photo, photo_small) VALUES (?,?,?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, userDataEntity.getUsername());
            ps.setString(2, userDataEntity.getCurrency().name());
            ps.setString(3, userDataEntity.getFirstname());
            ps.setString(4, userDataEntity.getSurname());
            ps.setBytes(5, userDataEntity.getPhoto());
            ps.setBytes(6, userDataEntity.getPhotoSmall());
            return ps;
        }, kh);

        userDataEntity.setId((UUID)kh.getKeys().get("id"));
        return userDataEntity;
    }

    @Override
    public Optional<UserDataEntity> findUserInUserDataByID(UUID id) {
        try {
            return Optional.of(userDataTemplate.queryForObject("SELECT * FROM \"user\" WHERE id = ?",
                    UserEntityRowMapper.INSTANCE,
                    id));
        } catch (DataRetrievalFailureException e) {
            return Optional.empty();
        }
    }
}

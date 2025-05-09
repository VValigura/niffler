package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.DataBase;
import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserDataEntity;
import guru.qa.niffler.data.jpa.EmProvider;
import jakarta.persistence.EntityManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

public class UserRepositoryHibernate implements UserRepository {

    private final EntityManager authEm = EmProvider.getEntityManager(DataBase.AUTH);
    private final EntityManager userDataEm = EmProvider.getEntityManager(DataBase.USERDATA);
    private static final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    public UserAuthEntity createUserInAuth(UserAuthEntity userAuthEntity) {
        userAuthEntity.setPassword(passwordEncoder.encode(userAuthEntity.getPassword()));
        authEm.persist(userAuthEntity);
        return userAuthEntity;
    }

    @Override
    public UserDataEntity createUserInUserData(UserDataEntity userDataEntity) {
        userDataEm.persist(userDataEntity);
        return null;
    }

    @Override
    public Optional<UserDataEntity> findUserInUserDataByID(UUID id) {

        return Optional.ofNullable(userDataEm.find(UserDataEntity.class, id));
    }
}

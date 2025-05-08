package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserDataEntity;

import java.util.Optional;
import java.util.UUID;

public class UserRepositoryHibernate implements UserRepository {
    @Override
    public UserAuthEntity createUserInAuth(UserAuthEntity userAuthEntity) {
        return null;
    }

    @Override
    public UserDataEntity createUserInUserData(UserDataEntity userDataEntity) {
        return null;
    }

    @Override
    public Optional<UserDataEntity> findUserInUserDataByID(UUID id) {
        return Optional.empty();
    }
}

package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserDataEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    static UserRepository getInstance() {
        if("sjbc".equals(System.getProperty("user.repository"))) {
            return new UserRepositorySpring();
        }

        if ("hibernate".equals(System.getProperty("user.repository"))) {
            return new UserRepositoryHibernate();
        }

        return new UserRepositoryJdbc();

    }


    UserAuthEntity createUserInAuth(UserAuthEntity userAuthEntity);

    UserDataEntity createUserInUserData(UserDataEntity userDataEntity);

    public Optional<UserDataEntity> findUserInUserDataByID(UUID id);
}

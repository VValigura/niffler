package guru.qa.niffler.data.jpa;

import guru.qa.niffler.data.DataBase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum EmProvider {
    INSTANCE;

    private final Map<DataBase, EntityManagerFactory>  store = new ConcurrentHashMap<>();


    private EntityManagerFactory computeEfm(DataBase db) {
        return store.computeIfAbsent(db,key -> {
            Map<String, String> props = new HashMap<>();
            props.put("hibernate.connection.url", db.getP6spyUrl());
            props.put("hibernate.connection.username", "postgres");
            props.put("hibernate.connection.password", "secret");
            props.put("hibernate.connection.driver_class", "com.p6spy.engine.spy.P6SpyDriver");
            props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

            return Persistence.createEntityManagerFactory("niffler-st5", props);
        });
    }

    public static EntityManager getEntityManager(DataBase dataBase) {
        return new TransactionalEntityManager( new ThreadSafeEntityManager(EmProvider.INSTANCE.computeEfm(dataBase).createEntityManager())

        );
    }
}

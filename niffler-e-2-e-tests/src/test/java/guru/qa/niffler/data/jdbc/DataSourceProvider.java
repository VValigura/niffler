package guru.qa.niffler.data.jdbc;

import guru.qa.niffler.data.DataBase;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum DataSourceProvider {
    INSTANCE;

    private final Map<DataBase, DataSource>  store = new ConcurrentHashMap<DataBase, DataSource>();


    private DataSource computeDataSource(DataBase db) {
        return store.computeIfAbsent(db,key -> {
            PGSimpleDataSource dataSource = new PGSimpleDataSource();
            dataSource.setURL(db.getJdbcURL());
            dataSource.setUser("postgres");
            dataSource.setPassword("secret");
            return dataSource;
        });
    }

    public static DataSource getDataSource(DataBase dataBase) {
        return DataSourceProvider.INSTANCE.computeDataSource(dataBase);
    }
}

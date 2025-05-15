package guru.qa.niffler.data;

import guru.qa.niffler.config.Config;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DataBase {
    AUTH("jdbc:postgresql://%s:%d/niffler-auth"),
    CURRENCY("jdbc:postgresql://localhost:5432/niffler-currency"),
    SPEND("jdbc:postgresql://localhost:5432/niffler-spend"),
    USERDATA("jdbc:postgresql://localhost:5432/niffler-userdata");

    private static  final Config CFG = Config.getInstance();

    public String getJdbcURL() {
        return String.format(jdbcURL, CFG.dbHost(), CFG.dbPort());
    }

    private final String jdbcURL;
}

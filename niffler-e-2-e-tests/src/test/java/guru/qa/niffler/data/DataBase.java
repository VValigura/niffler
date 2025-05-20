package guru.qa.niffler.data;

import guru.qa.niffler.config.Config;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public enum DataBase {
    AUTH("jdbc:postgresql://%s:%d/niffler-auth"),
    CURRENCY("jdbc:postgresql://localhost:5432/niffler-currency"),
    SPEND("jdbc:postgresql://localhost:5432/niffler-spend"),
    USERDATA("jdbc:postgresql://localhost:5432/niffler-userdata");

    private final String jdbcURL;

    private static  final Config CFG = Config.getInstance();

    public String getJdbcURL() {
        return String.format(jdbcURL, CFG.dbHost(), CFG.dbPort());
    }



    public String getP6spyUrl(){
        return "jdbc:p6spy:" + StringUtils.substringAfter(getJdbcURL(), ":");
    }
}

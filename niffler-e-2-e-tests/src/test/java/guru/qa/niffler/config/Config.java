package guru.qa.niffler.config;

public interface Config {

    static Config getInstance(){
        if ("docker".equals(System.getProperty("test.env" ))) {
            return DockerConfig.INSTANCE;
        } else if ("local".equals(System.getProperty("test.env", "local"))) {
            return LocalConfig.INSTANCE;
        } else {
            throw new IllegalArgumentException("Unknown test environment: " + System.getProperty("test.env"));
        }
    }

    String frontUrl();

    String spendUrl();

    String authUrl();

    String dbHost();

    default int dbPort() {
        return 5432;
    }

}

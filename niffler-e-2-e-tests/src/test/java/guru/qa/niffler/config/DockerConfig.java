package guru.qa.niffler.config;

public class DockerConfig implements Config{

    static final DockerConfig INSTANCE = new DockerConfig();

    private DockerConfig() {
    }

    @Override
    public String frontUrl() {
        return "http://frontend.niffler.dc/";
    }

    @Override
    public String spendUrl() {
        return "http://spend.niffler.dc:8093/";
    }

    @Override
    public String authUrl() {
        return "";
    }

    @Override
    public String dbHost() {
        return "niffler-all-db";
    }
}

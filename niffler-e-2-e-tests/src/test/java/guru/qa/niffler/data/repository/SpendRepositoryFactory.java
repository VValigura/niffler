package guru.qa.niffler.data.repository;

public class SpendRepositoryFactory {
    private SpendRepositoryFactory() {
    }

    public static SpendRepository spendRepository() {
        if ("hibernate".equals(System.getProperty("repo"))) {
            return new SpendRepositoryHibernate();
        }
        if ("SPRING".equals(System.getProperty("repo"))) {
            return new SpendRepositorySpringJDBC();
        }
        return new SpendRepositoryJdbc();
    }
}

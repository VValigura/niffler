package guru.qa.niffler.data.repository;

public class SpendRepositoryFactory {
    private SpendRepositoryFactory() {
    }

    public static SpendRepository getSpendRepository() {
//        if (System.getProperty("spend.repository").equalsIgnoreCase("hibernate")) {
//            return new SpendRepositoryHibernate();
//        } else if (System.getProperty("spend.repository").equalsIgnoreCase("spring")) {
//            return new SpendRepositorySpring();
//        } else {
//            return new SpendRepositoryJdbc();
//        }

        return new SpendRepositoryJdbc();
    }
}

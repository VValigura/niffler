package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.DataBase;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.jpa.EmProvider;
import jakarta.persistence.EntityManager;

public class SpendRepositoryHibernate implements SpendRepository {

    private final EntityManager em = EmProvider.getEntityManager(DataBase.SPEND);

    @Override
    public CategoryEntity createCategory(CategoryEntity categoryEntity) {
        em.persist(categoryEntity);
        return categoryEntity;
    }

    @Override
    public void deleteCategory(CategoryEntity categoryEntity) {
        em.remove(categoryEntity);
    }


    @Override
    public SpendEntity createSpend(SpendEntity spendEntity) {
        em.persist(spendEntity);
        return spendEntity;
    }

    @Override
    public void deleteSpend(SpendEntity spendEntity) {
        em.remove(spendEntity);
    }

}

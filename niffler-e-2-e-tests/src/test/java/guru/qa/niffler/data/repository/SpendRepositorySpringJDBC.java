package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;

public class SpendRepositorySpringJDBC  implements SpendRepository {
    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        return null;
    }

    @Override
    public void removeCategory(CategoryEntity category) {

    }

    @Override
    public CategoryEntity updateCategory(CategoryEntity category, String newCategory) {
        return null;
    }

    @Override
    public SpendEntity createSpend(SpendEntity spend) {
        return null;
    }

    @Override
    public void removeSpend(SpendEntity spend) {

    }

    @Override
    public SpendEntity updateSpend(SpendEntity spend, Double newAmount)  {
        return null;
    }
}

package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;

public interface SpendRepository {

    CategoryEntity createCategory(CategoryEntity categoryEntity);
    void deleteCategory(CategoryEntity categoryEntity);

    SpendEntity createSpend(SpendEntity spendEntity);
    void deleteSpend(SpendEntity spendEntity);
}

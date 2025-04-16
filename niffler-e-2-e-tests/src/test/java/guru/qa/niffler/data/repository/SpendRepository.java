package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;

public interface SpendRepository {
    CategoryEntity createCategory(CategoryEntity category);

    void removeCategory(CategoryEntity category);

    CategoryEntity updateCategory(CategoryEntity category, String newCategory);

    SpendEntity createSpend(SpendEntity spend);

    void removeSpend(SpendEntity spend);

    SpendEntity updateSpend(SpendEntity spend, Double newAmount) ;





}

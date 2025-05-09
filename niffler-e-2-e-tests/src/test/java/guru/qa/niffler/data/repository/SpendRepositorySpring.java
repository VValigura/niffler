package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.DataBase;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.jdbc.DataSourceProvider;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.UUID;

public class SpendRepositorySpring implements SpendRepository {

    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(
            DataSourceProvider.getDataSource(DataBase.SPEND)
    );

    @Override
    public CategoryEntity createCategory(CategoryEntity categoryEntity) {
        KeyHolder kh = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO category (category, username) values (?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, categoryEntity.getCategory());
            ps.setString(2, categoryEntity.getUsername());
            return ps;
        }, kh);
        categoryEntity.setId((UUID)kh.getKeys().get("id"));
        return categoryEntity;
    }

    @Override
    public void deleteCategory(CategoryEntity categoryEntity) {
        jdbcTemplate.update(
                "delete from category where id = ?",
                categoryEntity.getId()
        );
    }

    @Override
    public SpendEntity createSpend(SpendEntity spendEntity) {
        KeyHolder kh = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(
                    "insert into spend (username, spend_date, currency, amount, description, category_id) values (?,?,?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, spendEntity.getUsername());
            ps.setDate(2, new java.sql.Date(spendEntity.getSpendDate().getTime()));
            ps.setString(3, spendEntity.getCurrency().toString());
            ps.setDouble(4, spendEntity.getAmount());
            ps.setString(5, spendEntity.getDescription());
            ps.setObject(6, spendEntity.getCategory().getId());
            return ps;
        }, kh);

        spendEntity.setId((UUID)kh.getKeys().get("id"));
        return spendEntity;
    }

    @Override
    public void deleteSpend(SpendEntity spendEntity) {
        jdbcTemplate.update(
                "delete from spend where id = ?",
                spendEntity.getId()
        );
    }

}

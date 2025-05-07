package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.DataBase;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.jdbc.DataSourceProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SpendRepositoryJdbc implements SpendRepository{
    private static final DataSource dataSource = DataSourceProvider.getDataSource(DataBase.SPEND);

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO category (category, username) values (?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, category.getCategory());
            ps.setString(2, category.getUsername());
            ps.executeUpdate();

            UUID id = null;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    id = UUID.fromString(rs.getString("id"));
                }
            }
            category.setId(id);
            return category;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteCategory(CategoryEntity category) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("delete from category where id = ?")) {
            ps.setObject(1, category.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SpendEntity createSpend(SpendEntity spendEntity) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("insert into spend (username, spend_date, currency, amount, description, category_id) values (?,?,?,?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, spendEntity.getUsername());
            ps.setDate(2, new java.sql.Date(spendEntity.getSpendDate().getTime()));
            ps.setString(3, spendEntity.getCurrency().toString());
            ps.setDouble(4, spendEntity.getAmount());
            ps.setString(5, spendEntity.getDescription());
            ps.setObject(6, UUID.fromString(spendEntity.getCategory()));
            ps.executeUpdate();

            UUID id = null;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    id = UUID.fromString(rs.getString("id"));
                }
            }
            spendEntity.setId(id);
            return spendEntity;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteSpend(SpendEntity spendEntity) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("delete from spend where id = ?")) {
            ps.setObject(1, spendEntity.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

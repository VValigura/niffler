package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.DataBase;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.jdbc.DataBaseProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SpendRepositoryJdbc implements SpendRepository{

    private static final DataSource spendDataSource = DataBaseProvider
            .dataSource(DataBase.SPEND);

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        try (Connection conn = spendDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("insert into category (category, username) values (?,?)",
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
    public void removeCategory(CategoryEntity category) {
        try (Connection conn = spendDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("delete from category where id = ?")) {
            ps.setObject(1, category.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CategoryEntity updateCategory(CategoryEntity category, String newCategory) {
        try (Connection conn = spendDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("update category set category = ? where id = ?",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, newCategory);
            ps.setString(2, category.getId().toString());
            ps.executeUpdate();

            category.setCategory(newCategory);
            return category;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SpendEntity createSpend(SpendEntity spend) {
        try (Connection conn = spendDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("insert into spend (username, spend_date, currency, amount, description, category_id) values (?,?,?,?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, spend.getUsername());
            ps.setDate(2, new java.sql.Date(spend.getSpendDate().getTime()));
            ps.setString(3, spend.getCurrency().toString());
            ps.setDouble(4, spend.getAmount());
            ps.setString(5, spend.getDescription());
            ps.setObject(6, spend.getCategory().getId());
            ps.executeUpdate();

            UUID id = null;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    id = UUID.fromString(rs.getString("id"));
                }
            }
            spend.setId(id);
            return spend;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeSpend(SpendEntity spend) {
        try (Connection conn = spendDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("delete from spend where id = ?")) {
            ps.setObject(1, spend.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public SpendEntity updateSpend(SpendEntity spend, Double newAmount) {
        try (Connection conn = spendDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("update spend set amount = ? where id = ?",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, newAmount);
            ps.setString(2, spend.getId().toString());
            ps.executeUpdate();

            spend.setAmount(newAmount);
            return spend;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

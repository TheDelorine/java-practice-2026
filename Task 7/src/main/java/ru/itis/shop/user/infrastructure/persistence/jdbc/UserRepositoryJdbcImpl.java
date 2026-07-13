package ru.itis.shop.user.infrastructure.persistence.jdbc;

import ru.itis.shop.infrastructure.persistence.jdbc.RowMapper;
import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJdbcImpl implements UserRepository {

    private final DataSource dataSource;

    private final RowMapper<User> userRowMapper = row -> new User(
            row.getInt("id"),
            row.getString("name"),
            row.getString("email"),
            row.getString("password"),
            row.getString("profileDescription")
    );

    public UserRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(User user) {

        try (Connection connection = dataSource.getConnection()) {

            String sql =
                    "insert into account(name, email, password, profileDescription) " +
                            "values (?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getProfileDescription());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {

        try (Connection connection = dataSource.getConnection()) {

            String sql = "select * from account where email = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(userRowMapper.mapRow(resultSet));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findById(Integer id) {

        try (Connection connection = dataSource.getConnection()) {

            String sql = "select * from account where id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(userRowMapper.mapRow(resultSet));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from account");

            while (resultSet.next()) {
                users.add(userRowMapper.mapRow(resultSet));
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return users;
    }

    @Override
    public List<User> findAllByProfileDescription(String profileDescription) {

        List<User> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {

            String sql = "select * from account where profileDescription = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, profileDescription);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                users.add(userRowMapper.mapRow(resultSet));
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return users;
    }

    @Override
    public void updateProfileDescription(String email,
                                         String profileDescription) {

        try (Connection connection = dataSource.getConnection()) {

            String sql =
                    "update account " +
                            "set profileDescription = ? " +
                            "where email = ?";

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setString(1, profileDescription);
            statement.setString(2, email);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
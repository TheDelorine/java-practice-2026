package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJdbcImpl implements UserRepository {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/shop_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "050806";


    @Override
    public void save(User user) {
        //
    }

    @Override
    public User findById(String id) {
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void update(User user) {
        //
    }

    @Override
    public List<User> findAll() {
        List<User> listUser = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("select  * from account")) {
                    while (resultSet.next()) {
                        User user = new User(resultSet.getString("id"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getString("profileDescription"));
                        listUser.add(user);
                    }
                    return listUser;
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

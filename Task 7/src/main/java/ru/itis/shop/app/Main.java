package ru.itis.shop.app;

import ru.itis.shop.infrastructure.config.PropertiesReader;
import ru.itis.shop.infrastructure.persistence.jdbc.DriverManagerDataSource;
import ru.itis.shop.user.api.UserConsoleOperations;
import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.infrastructure.persistence.jdbc.UserRepositoryJdbcImpl;
import ru.itis.shop.user.repository.UserRepository;

import javax.sql.DataSource;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        PropertiesReader propertiesReader =
                new PropertiesReader();

        Properties properties =
                propertiesReader.read("application.properties");

        DataSource dataSource =
                new DriverManagerDataSource(
                        properties.getProperty("db.url"),
                        properties.getProperty("db.user"),
                        properties.getProperty("db.password")
                );

        UserRepository userRepository =
                new UserRepositoryJdbcImpl(dataSource);

        UserService userService =
                new UserService(userRepository);

        UserConsoleOperations operations =
                new UserConsoleOperations(userService);

        while (true) {
            operations.showMenu();
        }
    }
}
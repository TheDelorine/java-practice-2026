package ru.itis.shop.user.repository;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.infrastructure.persistence.UserMapper;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    User findById(String id);

    Optional<User> findByEmail(String email);

    void update(User user);

    List<User> findAll();
}

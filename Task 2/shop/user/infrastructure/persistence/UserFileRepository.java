package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserFileRepository implements UserRepository {

    private final String fileName;
    private final UserMapper userMapper;

    public UserFileRepository(String fileName, UserMapper userMapper) {
        this.fileName = fileName;
        this.userMapper = userMapper;
    }

    @Override
    public void save(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String id = UUID.randomUUID().toString();
            user.setId(id);
            writer.write(userMapper.toLine(user));
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public User findById(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split("\\|");
                if (arr[0].equals(id)) {
                    return userMapper.fromLine(line);
                }
            }
            return null;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split("\\|");
                if (arr[1].equals(email)) {
                    return Optional.ofNullable(userMapper.fromLine(line));
                }
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void update(User user) {
        File source = new File(fileName);
        File temp = new File(fileName + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(source));
             BufferedWriter writer = new BufferedWriter(new FileWriter(temp))) {

            String email = user.getEmail();
            String userString = userMapper.toLine(user);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length > 1 && parts[1].equals(email)) {
                    writer.write(userString);
                } else {
                    writer.write(line);
                }

                writer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        source.delete();
        temp.renameTo(source);
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }
}

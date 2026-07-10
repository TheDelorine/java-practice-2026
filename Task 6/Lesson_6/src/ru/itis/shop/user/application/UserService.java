package ru.itis.shop.user.application;

import ru.itis.shop.user.api.dto.UserDto;
import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(RuntimeException::new);

        return new UserDto(user.getId(), user.getEmail(), user.getProfileDescription());
    }

    public void signUp(String name, String email, String password, String profileDescription) {
        User user = new User(name, email, password, profileDescription);
        userRepository.save(user);
    }

    public boolean signIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get().getPassword().equals(password);
        } else return false;
    }

    public UserDto getUserByID(Integer id) {
        Optional<User> user = userRepository.findById(id);
        UserDto userDto = new UserDto(user.get().getId(), user.get().getEmail(), user.get().getProfileDescription());
        return userDto;
    }

    public void updateDescriptionByEmail(String email, String newDesc) {
        userRepository.updateDescriptionByEmail(email, newDesc);
    }

    public void infoByDesc(String description) {
        List<User> users =  userRepository.findByDescription(description);
        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    public void infoAll() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            System.out.println(user.toString());
        }
    }
}

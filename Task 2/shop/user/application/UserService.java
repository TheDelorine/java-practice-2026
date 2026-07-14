package ru.itis.shop.user.application;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(String email, String password, String profileDescription) {
        User user = new User(email, password, profileDescription);
        userRepository.save(user);
    }

    public boolean signIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get().getPassword().equals(password);
        } else return false;
    }

    public Optional<User> accountExist(String email) {
        return userRepository.findByEmail(email);
    }

    public void updateDescription(User user,String newDescription) {
        userRepository.update(new User(user.getId(), user.getEmail(), user.getPassword(),newDescription));
    }
    public void findAll() {
        List<User> listUser = userRepository.findAll();
        for (User user : listUser) {
            System.out.println(user.getId() + " | " + user.getEmail());
        }
    }
}

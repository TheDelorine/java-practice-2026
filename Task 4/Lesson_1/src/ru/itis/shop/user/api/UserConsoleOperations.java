package ru.itis.shop.user.api;

import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.util.Optional;
import java.util.Scanner;

public class UserConsoleOperations {

    private final UserService userService;
    private final Scanner scanner;

    public UserConsoleOperations(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        printUserMenu();

        String command = scanner.nextLine();

        switch (command) {
            case "1": {
                singUp();
            }
            break;
            case "2": {
                singIn();
            }
            break;
            case "3": {
                searchById();
            }
            break;
            case "4": {
                updateDescription();
            }
            break;
            case "5": {
                findAll();
            }
            break;
            case "0": {
                System.exit(0);
            }
        }
    }

    public void printUserMenu() {
        System.out.println("1. Регистрация пользователя");
        System.out.println("2. Вход в систему");
        System.out.println("3. Найти пользователя по id");
        System.out.println("4. Обновить информаци о пользователе.");
        System.out.println("5. Показать всех пользователей");
        System.out.println("0. Выход");
    }

    public void singUp() {
        System.out.println("Сейчас будем регистрировать пользователя");
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();
        System.out.println("Введите описание профиля:");
        String profileDescription = scanner.nextLine();
        userService.signUp(email, password, profileDescription);
    }

    public void singIn() {
        System.out.println("Вы можете войти в приложение");
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();

        if (userService.signIn(email, password)) {
            System.out.println("Вы вошли в приложение");
        } else {
            System.out.println("Email или пароль не верны");
        }
    }

    public void searchById() {
        System.out.println("Введите id: ");
        String id = scanner.nextLine();
        User findUser = userService.getUserRepository().findById(id);
        if (findUser != null) {
            System.out.println("Пользователь найден:");
            System.out.println(findUser);
        } else {
            System.out.println("Пользователь не найден.");
        }
    }

    public void updateDescription() {
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        Optional<User> user = userService.accountExist(email);
        if (user.isPresent()) {
            System.out.println("Введите новое описание:");
            String newDescription = scanner.nextLine();
            userService.updateDescription(user.get(), newDescription);
            System.out.println("Описание успешно изменено");
        } else {
            System.out.println("Аккаунт не найден.");
        }
    }

    public void findAll() {
        userService.findAll();
    }
}

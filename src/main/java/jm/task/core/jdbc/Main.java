package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Max", "MAximov", (byte) 22);
        userService.saveUser("IVAN", "Ivanov", (byte) 44);
        userService.saveUser("Alexey", "Alexeev", (byte) 23);
        userService.saveUser("Alexandr", "Alexandrovich", (byte) 13);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}

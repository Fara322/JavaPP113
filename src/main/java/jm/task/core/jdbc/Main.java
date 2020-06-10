package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        User user_1 = new User("Timer", "TIMATI", (byte) 38);
        User user_2 = new User("Oleg", "LSP", (byte) 32);
        User user_3 = new User("Albert", "KIEVSTONER", (byte) 29);
        User user_4 = new User("Pasha", "Technik", (byte) 38);

        userService.createUsersTable();

        userService.saveUser(user_1.getName(), user_1.getLastName(), user_1.getAge());
        userService.saveUser(user_2.getName(), user_2.getLastName(), user_2.getAge());
        userService.saveUser(user_3.getName(), user_3.getLastName(), user_3.getAge());
        userService.saveUser(user_4.getName(), user_4.getLastName(), user_4.getAge());

        List<User> list = userService.getAllUsers();
        for (User users : list) {
            System.out.println(users);
        }

        userService.cleanUsersTable();
    }
}

package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static Connection connection = Util.getInstance().getConnection();

    UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();

    // Создание таблицы
    public void createUsersTable() {
        userDaoHibernate.createUsersTable();
    }

    // Удаление таблицы
    public void dropUsersTable() {
        userDaoHibernate.dropUsersTable();
    }

    // Добавление юзера
    public void saveUser(String name, String lastName, byte age) {
        userDaoHibernate.saveUser(name, lastName, age);
    }

    // Удаление User из таблицы ( по id )
    public void removeUserById(long id) {
        userDaoHibernate.removeUserById(id);
    }

    // Получение всех юзеров
    public List<User> getAllUsers() {
        return userDaoHibernate.getAllUsers();
    }

    // Очистка таблицы
    public void cleanUsersTable() {
        userDaoHibernate.cleanUsersTable();
    }
}
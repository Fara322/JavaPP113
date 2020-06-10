package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static Connection connection = Util.getInstance().getConnection();

    public UserDaoJDBCImpl() {

    }

    // Создание таблицы
    public void createUsersTable() {
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("CREATE TABLE IF EXISTS users " +
                    "(id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255) NOT NULL, lastName VARCHAR(255) NOT NULL, age INT NOT NULL)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление таблицы
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Добавление Юзера
    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")){
            prepareStatement.setString(1, name);
            prepareStatement.setString(2, lastName);
            prepareStatement.setByte(3, age);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление Юзера по Ид
    public void removeUserById(long id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Получение списка всех юзеров
    public List<User> getAllUsers() {
        List <User> users = new ArrayList<>();
        String query = "select * from users";

        try(ResultSet resultSet = connection.createStatement().executeQuery(query)){
            while (resultSet.next()){
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Очищение таблицы
    public void cleanUsersTable() {
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

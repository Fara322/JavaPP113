package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static Connection connection = Util.getInstance().getConnection();
    private final SessionFactory sessionFactory = Util.getConnectionHibernet();

    // Создание таблицы
    @Override
    public void createUsersTable() {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS user" +
                " (id mediumint not null auto_increment, name VARCHAR(50), " +
                "lastname VARCHAR(50), " +
                "age tinyint, " +
                "PRIMARY KEY (id))").executeUpdate();
        transaction.commit();
        System.out.println("Таблица создана");
        session.close();
    }

    // Удаление таблицы юзеров
    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("Drop table if exists user").executeUpdate();
        transaction.commit();
        System.out.println("Таблица удалена");
        session.close();
    }

    // Добавление юзера в таблицу
    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(new User(name, lastName, age));
        transaction.commit();
        System.out.println("User с именем – " + name + " добавлен в базу данных");
        session.close();

    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.get(User.class, id));
        transaction.commit();
        System.out.println("Юзер успешно удален");
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        list = session.createCriteria(User.class).list();
        transaction.commit();
        session.close();

        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        final List<User> instances = session.createCriteria(User.class).list();

        for (Object o : instances) {
            session.delete(o);
        }

        transaction.commit();
        System.out.println("Таблица успешно очищена");
        session.close();

    }
}

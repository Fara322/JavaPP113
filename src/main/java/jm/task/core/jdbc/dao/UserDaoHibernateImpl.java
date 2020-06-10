package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    private final SessionFactory sessionFactory = Util.getConnectionHibernet();

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF EXISTS user " +
                "(id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255) NOT NULL, lastName VARCHAR(255) NOT NULL, age INT NOT NULL)");
        transaction.commit();
        System.out.println("Таблица успешно создана");
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("Drop table if exists user").executeUpdate();
        transaction.commit();
        System.out.println("Таблица успешно удалена");
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(new User(name, lastName, age));
        transaction.commit();
        System.out.println("Юзер с фамилией: " + lastName + "успешно добавлен.");
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.get(User.class, id));
        transaction.commit();
        System.out.println("Юзер с id: " + id + "успешно удален.");
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
        final List<User> example = session.createCriteria(User.class).list();
        for (Object o : example){
            session.delete(o);
        }
        transaction.commit();
        System.out.println("Таблица успешно очищена");
        session.close();
    }
}

package jm.task.core.jdbc.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getConnectionHibernet();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS user" +
                " (id mediumint not null auto_increment, name VARCHAR(255), " +
                "lastname VARCHAR(255), " +
                "age tinyint, " +
                "PRIMARY KEY (id))").executeUpdate();
        transaction.commit();
        System.out.println("Таблица успешно создана");
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
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
        System.out.println("Юзер с именем – " + name + " добавлен в базу данных");
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.get(User.class, id));
        transaction.commit();
        System.out.println("Юзер с id: " + id + " удален");
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

        session.getTransaction().commit();
        System.out.println("Таблица успешно очищена");
        session.close();
    }

}

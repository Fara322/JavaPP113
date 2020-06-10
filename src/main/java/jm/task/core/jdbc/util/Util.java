package jm.task.core.jdbc.util;

//import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class Util {

    private static Util instance = null;
    private static Connection connection = null;
    private static SessionFactory sessionFactory = null;
    private static final String URL = "jdbc:mysql://localhost:3306/user_info?useSSL=false&serverTimezone=Europe/Moscow";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

        public Connection getConnection() {
            try {
                if(connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                }
            } catch (SQLException e) {
                System.out.println("Проблемы с подключением к БД");
                e.printStackTrace();
            }
            return connection;
        }

        public static Util getInstance(){
            if (instance == null){
                instance = new Util();
            }
            return instance;
        }

    public static SessionFactory getConnectionHibernet() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties setting = new Properties();
                setting.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                setting.put(Environment.URL, "jdbc:mysql://localhost:3306/user_info?useSSL=false&serverTimezone=Europe/Moscow");
                setting.put(Environment.USER, "root");
                setting.put(Environment.PASS, "root");
                setting.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                setting.put(Environment.SHOW_SQL, "true");
                setting.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                setting.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(setting);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

}

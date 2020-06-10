package jm.task.core.jdbc.util;

//import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {

    private static Util instance = null;
    private static Connection connection = null;
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

}

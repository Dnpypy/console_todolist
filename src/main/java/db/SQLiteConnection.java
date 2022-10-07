package db;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SQLiteConnection {

    private static Connection con;
    private static String URL_KEY = "db.url";
    private static String DRIVER_DB = "db.driver";

    public static Connection getConnection() throws IOException {
        Properties properties = new Properties();
        try (var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /**
         *  Class.forName() возвращает сам объект класса!, в forName() передаю сам класс или путь до него
         */
        try {
            Driver driver = (Driver) Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
            String url = "jdbc:sqlite:db" + File.separator + "untitled.db";// указываем относительный путь к файлу БД

            try {
                con = DriverManager.getConnection(url);
                if (con == null) {
                    con = DriverManager.getConnection(url);
                }
                return con;
            } catch (SQLException e) {
                return null;
            }

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}

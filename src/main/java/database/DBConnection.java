package database;


import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Map;

/**
 * Класс отвечающий за подключение к базе данных
 */
public class DBConnection {
    /**
     * Поле с классом подключения к базе данных
     */
    private static Connection connection;

    /**
     * Метод который подключает пользователя к базе данных
     */
    public static void init() {
        try {
            Map<String, Map<String,Object>> data = new Yaml().load(new FileReader("src/main/resources/application.yml"));
            final String username = data.get("datasource").get("username").toString();
            final String password = data.get("datasource").get("password").toString();
            final String url = data.get("datasource").get("url").toString();

            connection = DriverManager.getConnection(url, username, password);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Statement getStatement() throws SQLException {
        return connection.createStatement();
    }

    public static Connection getConnection() {
        return connection;
    }
}

package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnection {
    private static Connection connection;

    public static void init() {
        final Properties properties = new Properties();

        try {
            final FileInputStream fileInputStream = new FileInputStream(
                    "D:/Clevertec/task/src/main/resources/application.properties");
            properties.load(fileInputStream);

            final String username = properties.getProperty("username");
            final String password = properties.getProperty("password");
            final String url = properties.getProperty("url");

            connection = DriverManager.getConnection(url, username, password);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Statement getStatement() throws SQLException {
        return connection.createStatement();
    }
}

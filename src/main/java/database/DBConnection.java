package database;


import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Map;
import java.util.Properties;


public class DBConnection {
    private static Connection connection;

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

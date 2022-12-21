package service;

import database.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryService {

    public static ResultSet get(String query) throws SQLException {
        Statement statement = DBConnection.getStatement();
        return statement.executeQuery(query);
    }
}

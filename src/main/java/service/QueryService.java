package service;

import database.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryService {

    public static ResultSet get(String query) throws SQLException {
        Statement statement = DBConnection.getStatement();
        try {
            return statement.executeQuery(query);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}

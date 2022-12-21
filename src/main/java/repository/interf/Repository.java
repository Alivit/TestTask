package repository.interf;

import model.Product;
import util.RequestUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Repository {
    void get(ResultSet result, RequestUtil request) throws SQLException;
}

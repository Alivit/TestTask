package repository.missed;

import model.Product;
import repository.interf.Repository;
import util.RequestUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MissedRepository implements Repository {

    @Override
    public void get(ResultSet result, RequestUtil request) throws SQLException {
        System.out.println("Репозиторий не найден!!!");
    }
}

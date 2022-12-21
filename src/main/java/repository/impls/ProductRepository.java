package repository.impls;

import model.Product;
import repository.interf.Repository;
import util.RequestUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductRepository implements Repository {

    @Override
    public void get(ResultSet result, RequestUtil request) throws SQLException {
        while (result.next()) {
            request.getProducts().add(new Product.Builder(result.getInt("id"),
                    result.getString("name"),
                    result.getDouble("price"),
                    result.getString("status")));

        }
    }

}

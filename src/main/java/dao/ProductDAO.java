package dao;

import entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    void add(Product.Builder product) throws SQLException;

    List<Product.Builder> getAll() throws SQLException;

    void update(Product.Builder product) throws SQLException;

    void remove(Product.Builder product) throws SQLException;
}

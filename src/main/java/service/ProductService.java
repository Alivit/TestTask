package service;

import caches.LRU;
import dao.ProductDAO;
import database.DBConnection;
import entity.Product;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductService implements ProductDAO {

    Connection connection = DBConnection.getConnection();
    PreparedStatement preparedStatement = null;
    Statement statement = null;
    Map<String, Map<String,Object>> data = new Yaml().load(new FileReader("src/main/resources/application.yml"));
    LRU<Product.Builder> lru = new LRU<>((Integer) data.get("cache").get("capacity"));

    public ProductService() throws FileNotFoundException {
    }

    @Override
    public void add(Product.Builder product) throws SQLException {

        String sql = "INSERT INTO product (name, price, status) VALUES (?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,product.build().getName());
            preparedStatement.setDouble(2,product.build().getPrice());
            preparedStatement.setString(3,product.build().getStatus());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (connection != null){
                connection.close();
            }
        }
    }

    @Override
    public List<Product.Builder> getAll() throws SQLException {
        int counter = 0;
        List<Product.Builder> productList = new ArrayList<>();

        String sql = "SELECT * FROM product";

        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){

                Product.Builder product = new Product.Builder();
                product.build().setId(resultSet.getInt("id"));
                product.build().setName(resultSet.getString("name"));
                product.build().setPrice(resultSet.getDouble("price"));
                product.build().setStatus(resultSet.getString("status"));
                lru.put(String.valueOf(counter), product);

                productList.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (connection != null){
                connection.close();
            }
        }

        return productList;
    }

    @Override
    public void update(Product.Builder product) throws SQLException {
        String sql = "UPDATE product SET name=?, price=?, status=?";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, product.build().getName());
            preparedStatement.setDouble(2, product.build().getPrice());
            preparedStatement.setString(3, product.build().getStatus());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (connection != null){
                connection.close();
            }
        }
    }

    @Override
    public void remove(Product.Builder product) throws SQLException {
        String sql = "DELETE FROM product WHERE ID=?";

        try{
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,product.build().getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (connection != null){
                connection.close();
            }
        }

    }
}

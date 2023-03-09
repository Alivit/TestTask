package service;

import caches.LRU;
import dao.DiscountCardDAO;
import database.DBConnection;
import entity.DiscountCard;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DiscountCardService implements DiscountCardDAO {

    Connection connection = DBConnection.getConnection();
    PreparedStatement preparedStatement = null;
    Statement statement = null;
    Map<String, Map<String,Object>> data = new Yaml().load(new FileReader("src/main/resources/application.yml"));
    LRU<DiscountCard> lru = new LRU<>((Integer) data.get("cache").get("capacity"));

    public DiscountCardService() throws FileNotFoundException {
    }

    @Override
    public void add(DiscountCard card) throws SQLException {

        String sql = "INSERT INTO discount_card (code, discount) VALUES (?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,card.getCode());
            preparedStatement.setInt(2,card.getDiscount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<DiscountCard> getAll() throws SQLException {
        int counter = 0;
        List<DiscountCard> cardList = new ArrayList<>();

        String sql = "SELECT * FROM discount_card";

        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                DiscountCard card = new DiscountCard();
                card.setId(resultSet.getInt("id"));
                card.setCode(resultSet.getInt("code"));
                card.setDiscount(resultSet.getInt("discount"));
                lru.put(String.valueOf(counter), card);

                cardList.add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cardList;
    }

    @Override
    public void update(DiscountCard card) throws SQLException {
        String sql = "UPDATE discount_card SET code=?, discount=?";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, card.getCode());
            preparedStatement.setInt(2, card.getDiscount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(DiscountCard card) throws SQLException {
        String sql = "DELETE FROM discount_card WHERE ID=?";

        try{
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,card.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

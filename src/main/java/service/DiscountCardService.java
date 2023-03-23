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

/**
 * Класс сервис crud операций для работы с дисконтными картами
 */
public class DiscountCardService implements DiscountCardDAO {

    /**
     * Это поле класа получения подключения к базе данных
     * @see Connection
     */
    Connection connection = DBConnection.getConnection();
    /**
     * Это поле класса запросов к базе данных
     */
    PreparedStatement preparedStatement = null;
    /**
     * Это поле класса запросов к базе данных
     */
    Statement statement = null;
    /**
     * Это поле которое считывает данные с файла application.yml
     */
    Map<String, Map<String,Object>> data = new Yaml().load(new FileReader("src/main/resources/application.yml"));
    /**
     * Это поле класса кэша на основе lru
     */
    LRU<DiscountCard> lru = new LRU<>((Integer) data.get("cache").get("capacity"));

    public DiscountCardService() throws FileNotFoundException {
    }

    /**
     * Метод созданный для добавления данных в таблицу discount_card
     * @param card объект класса DiscountCard
     */
    @Override
    public void add(DiscountCard card) throws SQLException {

        String sql = "INSERT INTO discount_card (code, discount) VALUES (?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,card.getCode());
            preparedStatement.setInt(2,card.getDiscount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод чтения данных из таблицы discount_card
     * @return лист с полученными данными из базы данных
     */
    @Override
    public List<DiscountCard> getAll() throws SQLException {
        int counter = 0;
        List<DiscountCard> cardList = new ArrayList<>();

        String sql = "SELECT * FROM discount_card";

        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){

                DiscountCard card = new DiscountCard(
                        resultSet.getInt("id"),
                        resultSet.getString("code"),
                        resultSet.getInt("discount")
                );
                cardList.add(card);
                lru.put(String.valueOf(counter), card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cardList;
    }

    /**
     * Метод созданный для обновления данных в таблице discount_card
     * @param card объект класса DiscountCard
     */
    @Override
    public void update(DiscountCard card) throws SQLException {
        String sql = "UPDATE discount_card SET code=?, discount=?";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, card.getCode());
            preparedStatement.setInt(2, card.getDiscount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод созданный для удаления данных из таблицы discount_card по id
     * @param card объект класса DiscountCard
     */
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

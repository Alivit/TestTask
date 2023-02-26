package util;

import exception.CardOrProductException;
import model.DiscountCard;
import model.Product;
import model.Promotional;
import repository.factory.RepositoryManager;
import repository.interf.Repository;
import service.QueryService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestUtil {

    private ArrayList<Product.Builder> products;
    private ArrayList<DiscountCard> cards;
    private ArrayList<Promotional> promotional;

    private static List<Integer> codeCard;
    private static Map<Integer, Integer> orderMap;

    public static void parseRequest(String[] args) {
        codeCard = new ArrayList<>();
        orderMap = new HashMap<>();

        if(args.length == 0){
            System.out.println("Некорректный запрос");
            System.exit(0);
        }

        for (int i = 0; i < args.length; i++) {
            String[] word = args[i].split("-");

            String firstPart = word[0];
            String secondPart = word[1];

            try {
                if (isCardPair(firstPart, secondPart)) {
                    codeCard.add(Integer.parseInt(secondPart));
                } else if (isProductPair(firstPart,secondPart)) {
                    orderMap.put(Integer.parseInt(firstPart), Integer.parseInt(secondPart));
                } else {
                    throw new CardOrProductException("Некорректный запрос");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }

        if(orderMap.size() == 0){
            System.out.println("Некорректный запрос");
            System.exit(0);
        }

        System.out.println("Запрос добавлен!!!");
    }

    private static boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isProductPair(String firstPart, String secondPart) {
        return isNumber(firstPart) && isNumber(secondPart);
    }

    private static boolean isCardPair(String firstPart, String secondPart) {
        return firstPart.equals("card") && isNumber(secondPart);
    }

    public void createData(String type, String query,RequestUtil request) throws SQLException {
        Repository repository = RepositoryManager.getRepository(type);
        ResultSet resultSet = QueryService.get(query);
        repository.get(resultSet, request);
    }

    public void workWithBD(RequestUtil request) throws SQLException {
        products = new ArrayList<>();
        createData("PRODUCT", "select * from product", request);

        try {
            if (codeCard.size() != 0) {
                cards = new ArrayList<>();
                createData("DISCOUNT_CARD", "select * from discount_card", request);
            }
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }

    }

    public void comparison(){

        promotional = new ArrayList<>();
        for(Map.Entry<Integer,Integer> item : orderMap.entrySet()){
            for (int i = 0; i < products.size(); i++){
                int id = products.get(i).build().getId();
                if (id == item.getKey()){
                    priceCalculation(i, item);
                }
            }
        }
    }

    private void priceCalculation(int i, Map.Entry<Integer, Integer> item){

        double newPrice;
        products.get(i).setAmount(item.getValue());
        try {
            if(products.get(i).build().getStatus() == null) {
                throw new Exception("Статус дефолтный!!!");
            }
            else if(products.get(i).build().getStatus().equals("акция") && item.getValue() >= 5){
                newPrice = products.get(i).build().getPrice() * item.getValue()
                        - percent(products.get(i).build().getPrice() * item.getValue(), 10);
            }else{
                newPrice = products.get(i).build().getPrice() * item.getValue();
            }
            promotional.add(new Promotional(products.get(i), newPrice));
        }
        catch (NullPointerException e){
            e.getMessage();
        }
        catch (Exception e){
            newPrice = products.get(i).build().getPrice() * item.getValue();
            promotional.add(new Promotional(products.get(i), newPrice));
        }
    }

    public double percent(double Sum, int discount){
        return Sum / 100 * discount;
    }

    public Map<Integer, Integer> getOrderMap() {
        return orderMap;
    }

    public List<Integer> getCodeCard() {
        return codeCard;
    }

    public ArrayList<Product.Builder> getProducts() {
        return products;
    }

    public ArrayList<DiscountCard> getCards() {
        return cards;
    }

    public ArrayList<Promotional> getPromotional() {
        return promotional;
    }

    public void setOrderMap(Map<Integer, Integer> orderMap) {
        this.orderMap = orderMap;
    }

    public void setCodeCard(List<Integer> codeCard) {
        this.codeCard = codeCard;
    }

    public void setProducts(ArrayList<Product.Builder> products) {
        this.products = products;
    }

    public void setCards(ArrayList<DiscountCard> cards) {
        this.cards = cards;
    }

    public void setPromotional(ArrayList<Promotional> promotional) {
        this.promotional = promotional;
    }
}

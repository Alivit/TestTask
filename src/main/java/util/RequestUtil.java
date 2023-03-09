package util;

import exception.CardOrProductException;
import entity.DiscountCard;
import entity.Product;
import entity.Promotional;
import service.DiscountCardService;
import service.ProductService;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestUtil {

    private List<Product.Builder> products;
    private List<DiscountCard> cards;
    private List<Promotional> promotional;

    private static List<Integer> codeCard;
    private static Map<Integer, Integer> orderMap;

    public static void parseRequest(String[] args) {
        codeCard = new ArrayList<>();
        orderMap = new HashMap<>();

        if(args.length == 0){
            System.out.println("Uncorrected request");
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
                    throw new CardOrProductException("Uncorrected request");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }

        if(orderMap.size() == 0){
            System.out.println("Uncorrected request");
            System.exit(0);
        }

        System.out.println("Request will added!!!");
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

    public void workWithBD() throws SQLException, FileNotFoundException {
        DiscountCardService discountCardService = new DiscountCardService();
        ProductService productService = new ProductService();

        products = productService.getAll();
        try {
            if (codeCard.size() != 0) {
                cards = discountCardService.getAll();
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
                throw new Exception("Default status!!!");
            }
            else if(products.get(i).build().getStatus().equals("promotion") && item.getValue() >= 5){
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

    public List<Product.Builder> getProducts() {
        return products;
    }

    public List<DiscountCard> getCards() {
        return cards;
    }

    public List<Promotional> getPromotional() {
        return promotional;
    }

    public void setOrderMap(Map<Integer, Integer> orderMap) {
        this.orderMap = orderMap;
    }

    public void setCodeCard(List<Integer> codeCard) {
        this.codeCard = codeCard;
    }

    public void setProducts(List<Product.Builder> products) {
        this.products = products;
    }

    public void setCards(List<DiscountCard> cards) {
        this.cards = cards;
    }

    public void setPromotional(List<Promotional> promotional) {
        this.promotional = promotional;
    }
}

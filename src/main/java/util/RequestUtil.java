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

/**
 * Класс обработки запросов, один из самых главных класссов где
 * и происходят важные функции над созданием чека.
 *
 * @autor Чуйко Виталий
 * @version 1.0
 * @since   2023-03-08
 */
public class RequestUtil {

    /**
     * Это поле листа с продуктами
     */
    private List<Product.Builder> products;
    /**
     * Это поле листа с картами
     */
    private List<DiscountCard> cards;
    /**
     * Это поле листа с продуктами со скидкой
     */
    private List<Promotional> promotional;

    /**
     * Это поле листа с кодами карт, которые были введены пользователем
     */
    private static List<String> codeCard;
    /**
     * Это поле ключ-карты с количейтвом и айди продукции, которые были введены пользователем
     */
    private static Map<Integer, Integer> orderMap;

    /**
     * Это парсер в котором происходит расстуссовка полученных аргументов по коллекциям.
     * Если аргументов нет выведет ошибку
     *
     * @param args полученные номера и количество продукции,
     *             а также номера дисконтной карты.
     * @return ничего
     */
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
                    codeCard.add(secondPart);
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

    /**
     * Метод который проверяет число ли это.
     *
     * @param s с проверяемым числом
     * @return boolean
     */
    private static boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Метод проверки пары продукции.
     *
     * @param firstPart айди
     * @param secondPart количество
     * @return boolean
     */
    private static boolean isProductPair(String firstPart, String secondPart) {
        return isNumber(firstPart) && isNumber(secondPart);
    }

    /**
     * Метод проверки пары карт.
     *
     * @param firstPart ключевое слово card
     * @param secondPart номер карты
     * @return boolean
     */
    private static boolean isCardPair(String firstPart, String secondPart) {
        return firstPart.equals("card") && isNumber(secondPart);
    }

    /**
     * Метод который работает с запросами базы данных.
     * Здесь из базы данных приходит и записывается в листы
     * список продуктов и дисконтных карт
     */
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

    /**
     * Метод сравнения введённых пользователем айди продукций
     * с теми что есть в базе данных.
     */
    public void comparison(){

        promotional = new ArrayList<>();
        for(Map.Entry<Integer,Integer> item : orderMap.entrySet()){
            products.forEach(product ->{
                if (product.build().getId() == item.getKey()){
                    priceCalculation(product.build().getId() - 1, item);
                }
            });
        }
        System.out.println(promotional.size());
    }

    /**
     * Метод для рассчёта цены продукции с учтением его количества.
     * Также здесь учитываются продукция со скидкой и рассчитывается уже по новому
     * @param i айди продута найденного в базе данных
     * @param item ключ-карта которая содержит количество и айди продукта
     */
    private void priceCalculation(int i, Map.Entry<Integer, Integer> item){
        products.get(i).setAmount(item.getValue());
        double newPrice = products.get(i).build().getPrice() * item.getValue();
        if(products.get(i).build().getStatus() != null || item.getValue() >= 5) {
            newPrice = newPrice - percent(products.get(i).build().getPrice() * item.getValue(), 10);
        }
        promotional.add(new Promotional(products.get(i), newPrice));
    }

    /**
     * Метод для рассчёта итоговой цены со скидкой по карте если она есть.
     * Если скидочная карта не найдена вернёт просто итоговую цену
     *
     * @param total итоговая цена
     * @return итоговую цену с учётом скидки или без
     */
    public double discountCalculation(double total){
        try {
            for (int i = 0; i < cards.size(); i++) {
                if (cards.get(i).getCode().equals(codeCard.get(0))) {
                    return total - percent(total, cards.get(i).getDiscount());
                }
            }
        } catch (NullPointerException e) {
            return total;
        }
        return total;
    }

    /**
     * Метод для получение скидки
     *
     * @return скидку в процентах
     */
    public int getDiscount() {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getCode().equals(codeCard.get(0))) {
                return cards.get(i).getDiscount();
            }
        }
        return 0;
    }

    /**
     * Метод для рассчёта процента по скидке.
     *
     * @param Sum итогова сумма
     * @param discount скидка
     * @return сумму со скидкой
     */
    public double percent(double Sum, int discount){
        return Sum / 100 * discount;
    }

    public Map<Integer, Integer> getOrderMap() {
        return orderMap;
    }

    public List<String> getCodeCard() {
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

    public void setCodeCard(List<String> codeCard) {
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

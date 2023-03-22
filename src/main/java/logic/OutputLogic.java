package logic;

import util.RequestUtil;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Класс вывода чека в терминале
 */
public class OutputLogic {

    /**
     * Это поле формата для цены
     */
    private static final NumberFormat FORMATER = new DecimalFormat("#0.00");
    /**
     * Это поле даты
     */
    private static Date dateNow = new Date();
    /**
     * Это поле формата даты
     */
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(" yyyy.MM.dd 'and time' hh:mm:ss a zzz");
    /**
     * Это поле листа хранящее изображение чека первая половина
     */
    private static final List<String> check = List.of(
            "+--------------------------------------------------------+",
            "cash receipt 111",
            "IP Ivanov Ivan Ivanovich",
            "Minsk, Vokzalnaya street, 32",
            "Current date " + dateFormat.format(dateNow),
            "+--------------------------------------------------------+",
            "CASH CHECK/INCOME",
            "",
            "RN KTT 000006547677567756",
            "FN 87100065476564584",
            "+--------------------------------------------------------+",
            "name              price           amount        costs     ",
            "+--------------------------------------------------------+"
    );

    /**
     * Это поле листа хранящее изображение чека вторая половина
     */
    static List<String> checkList = new ArrayList<>(check);

    /**
     * Метод для выравнивания строки по центру
     *
     * @param width длина строки
     * @param s сама строка
     * @return возращает отформатирующуюся строку
     */
    public static String centerString (int width, String s) {
        return String.format("|%-" + width  + "s|", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    /**
     * Метод вывода продуктов в чеке
     *
     * @param total цена
     * @param request с списком продуктов
     * @return возращает итоговую цену
     */
    private static double printProduct(double total, RequestUtil request){
        for (int i = 0; i < request.getPromotional().size(); i++) {
            total = total + request.getPromotional().get(i).getNewPrice();
            checkList.add(String.format("%-17s %-18s %-10s %-10s", request.getPromotional().get(i).getName(),
                    request.getPromotional().get(i).getPrice(),
                    request.getPromotional().get(i).getAmount(),
                    FORMATER.format(request.getPromotional().get(i).getNewPrice())));
        }
        return total;
    }

    /**
     * Метод формирования чека
     *
     * @param request с списком продуктов
     * @return возращает изображения чека
     */
    public static List<String> getReceipt(RequestUtil request){
        double total = 0.0;
        total = printProduct(total, request);
        checkList.add("");
        total = discountCalculation(total, request);
        checkList.add(String.format("%-47s %-10s", "Total", FORMATER.format(total)));
        checkList.add("+--------------------------------------------------------+");
        checkList.add("THANK YOU FOR YOUR PURCHASE");
        checkList.add("+--------------------------------------------------------+");
        return checkList;
    }

    /**
     * Метод вывода чека нак экран
     */
    public static void viewReceipt(RequestUtil request){
        getReceipt(request).forEach(s-> System.out.println(centerString(58,s)));
    }

    /**
     * Метод вывода скидки за все продукты
     *
     * @param total цена
     * @param request с списком продуктов
     * @return возращает итоговую цену
     */
    public static double discountCalculation(double total, RequestUtil request){
        try {
            for (int i = 0; i < request.getCards().size(); i++) {
                if (request.getCards().get(i).getCode() == request.getCodeCard().get(0)) {
                    double newTotal = total - request.percent(total, request.getCards().get(i).getDiscount());
                    checkList.add(String.format("%-47s %-10s", "STARTING PRICE", FORMATER.format(total)));
                    checkList.add(String.format("%-47s %s%-8s", "DISCOUNT", request.getCards().get(i).getDiscount(),"%"));
                    return newTotal;
                }
            }
        } catch (NullPointerException e) {
            return total;
        }
        return total;
    }

    public static List<String> getCheckList() {
        return checkList;
    }

    public static void setCheckList(List<String> checkList) {
        OutputLogic.checkList = checkList;
    }
}

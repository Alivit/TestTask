package logic;

import entity.Promotional;
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
     * Это поле листа хранящее изображение заглавия чека
     */
    private static List<String> check = List.of(
            "+--------------------------------------------------------+",
            "CASH RECEIPT 111",
            "IP Ivanov Ivan Ivanovich",
            "Minsk, Vokzalnaya street, 32",
            "Current date " + dateFormat.format(dateNow),
            "+--------------------------------------------------------+",
            "CASH CHECK/INCOME",
            "",
            "RN KTT 000006547677567756",
            "FN 87100065476564584",
            "+--------------------------------------------------------+",
            "NAME              PRICE           AMOUNT        COSTS     ",
            "+--------------------------------------------------------+"
    );

    /**
     * Это поле листа хранящее изображение чека - вторая половина
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
     * Метод добавление всех продуктов в лист чека
     *
     * @param request со списком продуктов
     */
    private static void printProduct(RequestUtil request){
        for (int i = 0; i < request.getPromotional().size(); i++) {
            checkList.add(String.format("%-17s %-18s %-10s %-10s",
                    request.getPromotional().get(i).getName(),
                    request.getPromotional().get(i).getPrice(),
                    request.getPromotional().get(i).getAmount(),
                    FORMATER.format(request.getPromotional().get(i).getNewPrice())));
        }
    }

    /**
     * Метод формирования чека
     *
     * @param request с списком продуктов
     * @return возращает изображения чека
     */
    public static List<String> getReceipt(RequestUtil request){
        printProduct(request);
        checkList.add("");
        printTotalPrice(request);
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
     * Метод вывода общей цены за все продукты(со скидкой или без)
     *
     * @param request со списком продуктов
     */
    public static void printTotalPrice(RequestUtil request){
        double total = request.getPromotional().stream()
                .mapToDouble(Promotional::getNewPrice)
                .sum();
        double newTotal = request.discountCalculation(total);
        if(newTotal != total){
            checkList.add(String.format("%-47s %-10s", "STARTING PRICE", FORMATER.format(total)));
            checkList.add(String.format("%-47s %s%-8s", "DISCOUNT", request.getDiscount(),"%"));
        }
        checkList.add(String.format("%-47s %-10s", "Total", FORMATER.format(newTotal)));
    }

    public static List<String> getCheckList() {
        return checkList;
    }

    public static void setCheckList(List<String> checkList) {
        OutputLogic.checkList = checkList;
    }

    public static List<String> getCheck() {
        return check;
    }

    public static void setCheck(List<String> check) {
        OutputLogic.check = check;
    }
}

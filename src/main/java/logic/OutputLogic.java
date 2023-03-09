package logic;

import util.RequestUtil;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OutputLogic {

    private static final NumberFormat FORMATER = new DecimalFormat("#0.00");
    private static Date dateNow = new Date();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("E yyyy.MM.dd 'и время' hh:mm:ss a zzz");
    private static List<String> check = List.of(
            "+--------------------------------------------------------+",
            "Кассовый чек №111",
            "ИП Иванов Иван Иванович",
            "г.Минск ул.Вокзальная, 32",
            "Текущая дата " + dateFormat.format(dateNow),
            "+--------------------------------------------------------+",
            "КАССОВЫЙ ЧЕК/ПРИХОД",
            "",
            "РН ККТ 000006547677567756",
            "ФН 87100065476564584",
            "+--------------------------------------------------------+",
            "Наименование              Цена       Кол-во     Стоим-ть  ",
            "+--------------------------------------------------------+"
    );

    static List<String> checkList = new ArrayList<>(check);

    public static String centerString (int width, String s) {
        return String.format("|%-" + width  + "s|", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    private static double printProduct(double total, RequestUtil request){
        for (int i = 0; i < request.getPromotional().size(); i++) {
            total = total + request.getPromotional().get(i).getNewPrice();
            checkList.add(String.format("%-25s %-10s %-10s %-10s", request.getPromotional().get(i).getName(),
                    request.getPromotional().get(i).getPrice(),
                    request.getPromotional().get(i).getAmount(),
                    FORMATER.format(request.getPromotional().get(i).getNewPrice())));
        }
        return total;
    }

    public static List<String> getReceipt(RequestUtil request){
        double total = 0.0;
        total = printProduct(total, request);
        checkList.add("");
        total = discountCalculation(total, request);
        checkList.add(String.format("%-47s %-10s", "ИТОГО", FORMATER.format(total)));
        checkList.add("+--------------------------------------------------------+");
        checkList.add("СПАСИБО ЗА ПОКУПКУ");
        checkList.add("+--------------------------------------------------------+");
        return checkList;
    }

    public static void viewReceipt(RequestUtil request){
        getReceipt(request).forEach(s-> System.out.println(centerString(58,s)));
    }

    public static double discountCalculation(double total, RequestUtil request){
        try {
            for (int i = 0; i < request.getCards().size(); i++) {
                if (request.getCards().get(i).getCode() == request.getCodeCard().get(0)) {
                    double newTotal = total - request.percent(total, request.getCards().get(i).getDiscount());
                    checkList.add(String.format("%-47s %-10s", "НАЧАЛЬНАЯ ЦЕНА", FORMATER.format(total)));
                    checkList.add(String.format("%-47s %s%-8s", "СКИДКА", request.getCards().get(i).getDiscount(),"%"));
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

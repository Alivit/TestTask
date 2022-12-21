package file;

import util.RequestUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiptFile {

    private static final NumberFormat FORMATER = new DecimalFormat("#0.00");
    private static File file;

    public static void inputInFile(RequestUtil request)  {
        file = new File("D:\\Clevertec\\task\\src\\main\\resources\\Receipt.txt");
        try(FileWriter writer = new FileWriter(file, false)){
            PrintWriter print = new PrintWriter(writer);
            double total = 0.0;
            Date dateNow = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("E yyyy.MM.dd 'и время' hh:mm:ss a zzz");
            print.println("+----------------------------------------------------------+");
            print.println(centerString(58,"Кассовый чек №111"));
            print.println(centerString(58,"ИП Иванов Иван Иванович"));
            print.println(centerString(58,"г.Минск ул.Вокзальная, 32"));
            print.println(centerString(58,"Текущая дата " + dateFormat.format(dateNow)));
            print.println("+----------------------------------------------------------+");
            print.println(centerString(58,"КАССОВЫЙ ЧЕК/ПРИХОД"));
            print.println(centerString(58,""));
            print.println(centerString(58,"РН ККТ 000006547677567756"));
            print.println(centerString(58,"ФН 87100065476564584"));
            print.println("+----------------------------------------------------------+");
            print.printf("|%-25s %-10s %-10s %-10s|%n","Наименование","Цена","Кол-во","Стоим-ть");
            print.println("+----------------------------------------------------------+");
            total = printProduct(total,request, print);
            print.println(centerString(58,""));
            total = discountCalculation(total, request, print);
            print.printf("|%-47s %-10s|%n", "ИТОГО", FORMATER.format(total));
            print.println("+----------------------------------------------------------+");
            print.println(centerString(58,"СПАСИБО ЗА ПОКУПКУ"));
            print.println("+----------------------------------------------------------+");
            print.close();
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    private static double printProduct(double total, RequestUtil request, PrintWriter print){
        for (int i = 0; i < request.getPromotional().size(); i++) {
            total = total + request.getPromotional().get(i).getNewPrice();
            print.printf("|%-25s %-10s %-10s %-10s|%n", request.getPromotional().get(i).getName(),
                    request.getPromotional().get(i).getPrice(),
                    request.getPromotional().get(i).getAmount(),
                    FORMATER.format(request.getPromotional().get(i).getNewPrice()));
        }
        return total;
    }
    public static double discountCalculation(double total, RequestUtil request, PrintWriter print){
        try {
        for (int i = 0; i < request.getCards().size(); i++){
            if(request.getCards().get(i).getCode() == request.getCodeCard().get(0)){
                double newTotal = total - request.percent(total, request.getCards().get(i).getDiscount());
                print.printf("|%-47s %-10s|%n", "НАЧАЛЬНАЯ ЦЕНА", FORMATER.format(total));
                print.printf("|%-47s %s%-8s|%n", "СКИДКА", request.getCards().get(i).getDiscount(),"%");
                return newTotal;
            }
        }
        }catch (NullPointerException e){
            return total;
        }
        return total;
    }
    public static String centerString (int width, String s) {
        return String.format("|%-" + width  + "s|", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }
}

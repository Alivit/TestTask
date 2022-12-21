package logic;

import util.RequestUtil;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OutputLogic {

    private static final NumberFormat FORMATER = new DecimalFormat("#0.00");


    public static String centerString (int width, String s) {
        return String.format("|%-" + width  + "s|", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    private static double printProduct(double total, RequestUtil request){
        for (int i = 0; i < request.getPromotional().size(); i++) {
            total = total + request.getPromotional().get(i).getNewPrice();
            System.out.format("|%-25s %-10s %-10s %-10s|%n", request.getPromotional().get(i).getName(),
                    request.getPromotional().get(i).getPrice(),
                    request.getPromotional().get(i).getAmount(),
                    FORMATER.format(request.getPromotional().get(i).getNewPrice()));
        }
        return total;
    }

    public static void  getReceipt(RequestUtil request){
        double total = 0.0;
        Date dateNow = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E yyyy.MM.dd '� �����' hh:mm:ss a zzz");
        System.out.println("+----------------------------------------------------------+");
        System.out.println(centerString(58,"�������� ��� �111"));
        System.out.println(centerString(58,"�� ������ ���� ��������"));
        System.out.println(centerString(58,"�.����� ��.����������, 32"));
        System.out.println(centerString(58,"������� ���� " + dateFormat.format(dateNow)));
        System.out.println("+----------------------------------------------------------+");
        System.out.println(centerString(58,"�������� ���/������"));
        System.out.println(centerString(58,""));
        System.out.println(centerString(58,"�� ��� 000006547677567756"));
        System.out.println(centerString(58,"�� 87100065476564584"));
        System.out.println("+----------------------------------------------------------+");
        System.out.format("|%-25s %-10s %-10s %-10s|%n","������������","����","���-��","�����-��");
        System.out.println("+----------------------------------------------------------+");
        total = printProduct(total, request);
        System.out.println(centerString(58,""));
        total = discountCalculation(total, request);
        System.out.format("|%-47s %-10s|%n", "�����", FORMATER.format(total));
        System.out.println("+----------------------------------------------------------+");
        System.out.println(centerString(58,"������� �� �������"));
        System.out.println("+----------------------------------------------------------+");
    }

    public static double discountCalculation(double total, RequestUtil request){
        try {
            for (int i = 0; i < request.getCards().size(); i++) {
                if (request.getCards().get(i).getCode() == request.getCodeCard().get(0)) {
                    double newTotal = total - request.percent(total, request.getCards().get(i).getDiscount());
                    System.out.format("|%-47s %-10s|%n", "��������� ����", FORMATER.format(total));
                    System.out.format("|%-47s %s%-8s|%n", "������", request.getCards().get(i).getDiscount(), "%");
                    return newTotal;
                }
            }
        } catch (NullPointerException e) {
            return total;
        }
        return total;
    }
}

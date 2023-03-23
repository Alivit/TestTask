package file.impls;

import file.interf.Inputable;
import logic.OutputLogic;
import util.RequestUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Класс записи чека в txt файл
 */
public class ReceiptTXT implements Inputable {

    /**
     * Это поле указывающее на файл хранящий чек
     */
    private final static File FILE_SOURCE = new File("src/main/resources/Receipt.txt");

    /**
     * Метод который записывает чек в указанный файл
     *
     *  @param request содержит список продуктов
     */
    public void inputInFile(RequestUtil request)  {
        try(FileWriter writer = new FileWriter(FILE_SOURCE, false)){
            PrintWriter print = new PrintWriter(writer);
            OutputLogic.getCheckList().forEach(s -> print.println(centerString(58,s)));
            print.close();
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

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
}

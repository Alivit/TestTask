package file.impls;

import file.interf.Inputable;
import logic.OutputLogic;
import util.RequestUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Класс записи чека в файл
 */
public class ReceiptFile implements Inputable {

    /**
     * Это поле указывающее на файл хранящий чек
     */
    private static File file;

    /**
     * Метод который записывает чек в указанный файл
     */
    public void inputInFile(RequestUtil request)  {
        file = new File("src/main/resources/Receipt.txt");
        try(FileWriter writer = new FileWriter(file, false)){
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

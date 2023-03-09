package file;

import logic.OutputLogic;
import util.RequestUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReceiptFile {

    private static File file;

    public static void inputInFile(RequestUtil request)  {
        file = new File("..\\Testasknew\\src\\main\\resources\\Receipt.txt");
        try(FileWriter writer = new FileWriter(file, false)){
            PrintWriter print = new PrintWriter(writer);
            OutputLogic.getCheckList().forEach(s -> print.println(centerString(58,s)));
            print.close();
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static String centerString (int width, String s) {
        return String.format("|%-" + width  + "s|", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }
}

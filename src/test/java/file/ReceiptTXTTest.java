package file;

import builder.impls.PromotionalTest;
import file.factory.FileRepository;
import file.impls.ReceiptTXT;
import file.interf.Inputable;
import logic.OutputLogic;
import entity.Promotional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.RequestUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class ReceiptTXTTest {

    @ParameterizedTest
    @CsvSource(value = {"12,lol"})
    void testCenterString (int width, String s){
        String str = ReceiptTXT.centerString(width,s);

        assertThat(str).hasSize(14);
        assertThat(str.contains("lol")).isTrue();
    }

    @Test
    void testInputFile(){
        RequestUtil request = new RequestUtil();
        ArrayList<Promotional> promotionals = new ArrayList<>();
        File file = new File("..\\Testasknew\\src\\main\\resources\\Receipt.txt");

        promotionals.add(PromotionalTest.aPromotional().build());
        request.setPromotional(promotionals);

        OutputLogic.getReceipt(request);
        Inputable input = FileRepository.getRepository("TXT");
        input.inputInFile(request);

        try(FileReader reader = new FileReader(file)){
            Scanner scan = new Scanner(reader);
            String result = "";
            while (scan.hasNextLine()) {
                result+=scan.nextLine();
            }
            assertThat(result.contains("14,44")).isTrue();
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

}
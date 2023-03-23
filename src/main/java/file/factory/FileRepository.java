package file.factory;

import file.interf.Inputable;
import file.missed.MissedRepository;
import file.impls.ReceiptPDF;
import file.impls.ReceiptTXT;

/**
 * Класс выступающий в качестве фабрики
 */
public class FileRepository {

    /**
     * Метод возвращающий ссылку объекта на нужный класс
     */
    public static Inputable getRepository(String type) {
        return switch (type) {
            case "PDF" -> new ReceiptPDF();
            case "TXT" -> new ReceiptTXT();
            default -> new MissedRepository();
        };
    }
}

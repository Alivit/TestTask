import database.DBConnection;
import file.factory.FileRepository;
import file.impls.ReceiptPDF;
import file.interf.Inputable;
import logic.OutputLogic;
import util.RequestUtil;
/**
 * Класс запуска чека, здесь же и находится точка запуска программы.
 *
 * @autor Чуйко Виталий
 * @version 1.0
 * @since   2023-03-08
 */
public class CheckRunner {
    /**
     * Это поле поле класса обработчика запросов c ссылкой на него
     * @see RequestUtil
     */
    static RequestUtil request = new RequestUtil();

    /**
     * Это гланый метод в котором происходит создание чека.
     * В начале записываются полученные параметры.
     * Дальше подключение к базе данных.
     * После идет работа с самой бд и данными.
     * И в конце вывод чека на экран и в файл.
     * @param args используется для получения номера и количества продукции,
     *             и также номера дисконтной карты.
     * @return ничего
     */
    public static void main(String[] args) throws Exception {
        String[] lol = new String[]{"1-1","2-1", "3-9", "4-10", "card-8888"};
        RequestUtil.parseRequest(lol);
        DBConnection.init();
        request.workWithBD();
        request.comparison();
        OutputLogic.viewReceipt(request);
        Inputable input = FileRepository.getRepository("PDF");
        input.inputInFile(request);
    }
}
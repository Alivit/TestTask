import database.DBConnection;
import logic.OutputLogic;
import file.ReceiptFile;
import util.RequestUtil;

public class CheckRunner {
    static RequestUtil request = new RequestUtil();

    public static void main(String[] args) throws Exception{
        String[] lol = {"1-2", "3-4", "5-2", "card-1234"};
        RequestUtil.parseRequest(lol);
        DBConnection.init();
        request.workWithBD(request);
        request.comparison();
        OutputLogic.viewReceipt(request);
        ReceiptFile.inputInFile(request);
    }
}

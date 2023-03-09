import database.DBConnection;
import file.ReceiptFile;
import logic.OutputLogic;
import util.RequestUtil;

public class CheckRunner {
    static RequestUtil request = new RequestUtil();

    public CheckRunner() {
    }

    public static void main(String[] args) throws Exception {
        String[] lol = new String[]{"3-1","5-2", "6-2"};
        RequestUtil.parseRequest(lol);
        DBConnection.init();
        request.workWithBD();
        request.comparison();
        OutputLogic.viewReceipt(request);
        ReceiptFile.inputInFile(request);
    }
}
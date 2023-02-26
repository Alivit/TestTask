import database.DBConnection;
import logic.OutputLogic;
import file.ReceiptFile;
import util.RequestUtil;

public class CheckRunner {
    static RequestUtil request = new RequestUtil();

    public static void main(String[] args) throws Exception{
        RequestUtil.parseRequest(args);
        DBConnection.init();
        request.workWithBD(request);
        request.comparison();
        OutputLogic.viewReceipt(request);
        ReceiptFile.inputInFile(request);
    }
}

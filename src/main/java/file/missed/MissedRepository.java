package file.missed;

import file.interf.Inputable;
import util.RequestUtil;

public class MissedRepository implements Inputable {
    @Override
    public void inputInFile(RequestUtil request) {
        System.out.println("File not found");
    }
}

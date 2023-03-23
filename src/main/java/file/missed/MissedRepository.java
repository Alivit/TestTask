package file.missed;

import file.interf.Inputable;
import util.RequestUtil;

/**
 * Класс отвечающий за потерянный репозиторий
 */
public class MissedRepository implements Inputable {

    /**
     * Метод возвращающий ошибку при ненайденом нужном файле
     */
    @Override
    public void inputInFile(RequestUtil request) {
        System.out.println("File not found");
    }
}

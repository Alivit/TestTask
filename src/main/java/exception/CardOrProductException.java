package exception;

/**
 * Класс ошибок
 */
public class CardOrProductException extends Exception{
    /**
     * Поле хранящее сообщение об ошибке
     */
    private String message;

    public CardOrProductException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

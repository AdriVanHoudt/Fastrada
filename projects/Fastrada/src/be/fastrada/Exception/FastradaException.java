package be.fastrada.Exception;

/**
 * Created by bavo on 4/03/14.
 */
public class FastradaException extends Exception {
    public FastradaException() {
    }

    public FastradaException(String detailMessage) {
        super(detailMessage);
    }

    public FastradaException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}

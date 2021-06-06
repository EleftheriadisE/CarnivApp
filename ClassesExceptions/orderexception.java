package CarnivAPP.Exceptions;

public class OrderException extends Exception {
    private static final long serialVersionUID = 3L;

    public OrderException(final String message) {
        super(message);
    }

    public OrderException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

}
package CarnivAPP.Exceptions;

public class ProductException extends Exception {
    private static final long serialVersionUID = 1L;

    public ProductException(final String message) {
        super(message);
    }

    public ProductException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

}

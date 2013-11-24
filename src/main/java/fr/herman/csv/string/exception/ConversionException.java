package fr.herman.csv.string.exception;

public class ConversionException extends RuntimeException {

    private static final long serialVersionUID = 6091588172341403653L;

    public ConversionException(Throwable t) {
        super(t);
    }

    public ConversionException() {
        super();
    }

    public ConversionException(String message) {
        super(message);
    }

}

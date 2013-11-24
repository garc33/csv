package fr.herman.csv.string.exception;

import fr.herman.csv.string.StringHandler;

public class UnhandledTypeException extends ConversionException {

    private static final long serialVersionUID = -5209959669831694563L;

    public UnhandledTypeException(Throwable t) {
        super(t);
    }

    public UnhandledTypeException() {
        super();
    }

    public UnhandledTypeException(Class type, StringHandler handler) {
        super(String.format("type <%s> not handled by handler <%s>",
                type.getName(), handler.getClass().getName()));
    }

}

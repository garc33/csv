package fr.herman.csv.exception;

import java.io.IOException;

public class UnmarshallingException extends RuntimeException {

    private static final long serialVersionUID = 3437710296308028113L;

    public UnmarshallingException(IOException e) {
        super(e);
    }

    public UnmarshallingException(String message, IOException e) {
        super(message, e);
    }

}

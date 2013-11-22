package fr.herman.csv.exception;


public class MarshallingException extends RuntimeException {

    public MarshallingException(Exception ioe) {
        super(ioe);
    }

    private static final long serialVersionUID = -2949713279098228431L;

}

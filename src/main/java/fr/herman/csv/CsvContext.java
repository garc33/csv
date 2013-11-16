package fr.herman.csv;

import fr.herman.csv.string.StringHandler;
import fr.herman.csv.string.StringHandlerLookup;

public class CsvContext<T> {

    private static final String EMPTY = "";

    public static final char DEFAULT_SEPARATOR = ',';

    public static final String DEFAULT_LINE_SEPARATOR = System
            .getProperty("line.separator");

    public static final char DEFAULT_QUOTE = '\'';

    public static final char DEFAULT_COMMENT = '#';

    private StringHandlerLookup stringHandlerLookup;

    private boolean withHeader = true;

    private char separator = DEFAULT_SEPARATOR;

    private char quote = DEFAULT_QUOTE;

    private char comment = DEFAULT_COMMENT;

    private String lineSeparator = DEFAULT_LINE_SEPARATOR;

    private CsvContext() {
    }

    public static <T> CsvContext<T> create() {
        return new CsvContext();
    }

    public CsvMarshaller<T> newMarshaller() {
        return new CsvMarshaller<T>(this);
    }

    public CsvUnmarshaller<T> newUnmarshaller() {
        return new CsvUnmarshaller<T>(this);
    }

    public boolean isWithHeader() {
        return withHeader;
    }

    public void setWithHeader(boolean withHeader) {
        this.withHeader = withHeader;
    }

    public String marshall(Object object) {
        if (object == null) {
            return EMPTY;
        }
        StringHandler converter = stringHandlerLookup.lookup(object.getClass());
        return converter.marshall(object);
    }

    public <Q> Q unmarshall(Class<Q> clazz, String value) {
        StringHandler converter = stringHandlerLookup.lookup(clazz);
        return (Q) converter.unmarshall(clazz, value);
    }

    public char getSeparator() {
        return separator;
    }

    public void setSeparator(char separator) {
        this.separator = separator;
    }

    public char getQuote() {
        return quote;
    }

    public void setQuote(char quote) {
        this.quote = quote;
    }

    public char getComment() {
        return comment;
    }

    public void setComment(char comment) {
        this.comment = comment;
    }

    public StringHandlerLookup getStringHandlerLookup() {
        return stringHandlerLookup;
    }

    public void setStringHandlerLookup(StringHandlerLookup stringHandlerLookup) {
        this.stringHandlerLookup = stringHandlerLookup;
    }

    public String getLineSeparator() {
        return lineSeparator;
    }

    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

}

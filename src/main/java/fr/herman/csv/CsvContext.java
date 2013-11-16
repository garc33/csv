package fr.herman.csv;

import fr.herman.csv.string.StringHandlerRegistry;

public class CsvContext<T> {

    private static final String EMPTY = "";

    public static final char DEFAULT_SEPARATOR = ',';

    public static final char DEFAULT_QUOTE = '\'';

    public static final char DEFAULT_COMMENT = '#';

    private final CsvBeanProperties csvBeanProperties;

    private StringHandlerRegistry registry;

    private boolean withHeader = true;

    private char separator = DEFAULT_SEPARATOR;

    private char quote = DEFAULT_QUOTE;

    private char comment = DEFAULT_COMMENT;

    private CsvContext(CsvBeanProperties csvBeanProperties) {
        this.csvBeanProperties = csvBeanProperties;
    }

    public static <T> CsvContext<T> create(Class<T> clazzer) {
        return new CsvContext(null);
    }

    public CsvMarshaller<T> newMarshaller() {
        return new CsvMarshaller<T>(this, csvBeanProperties);
    }

    public CsvUnmarshaller<T> newUnmarshaller() {
        return new CsvUnmarshaller<T>(this, csvBeanProperties);
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
        StringHandler converter = registry.lookup(object.getClass());
        return converter.marshall(object);
    }

    public <Q> Q unmarshall(Class<Q> clazz, String value) {
        StringHandler converter = registry.lookup(clazz);
        return (Q) converter.unmarshall(clazz, value);
    }

    public StringHandlerRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(StringHandlerRegistry registry) {
        this.registry = registry;
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

}

package fr.herman.csv;

import fr.herman.csv.factories.CsvReaderFactory;
import fr.herman.csv.factories.CsvWriterFactory;
import fr.herman.csv.factories.DefaultCsvFactory;

public class CsvContext<T> {

    private static final String EMPTY = "";

    public static final char DEFAULT_SEPARATOR = ',';

    public static final String DEFAULT_LINE_SEPARATOR = System.getProperty("line.separator");

    public static final char DEFAULT_QUOTE = '\'';

    public static final char DEFAULT_COMMENT = '#';

    private ConversionService converterService;

    private boolean withHeader = true;

    private char separator = DEFAULT_SEPARATOR;

    private char quote = DEFAULT_QUOTE;

    private char comment = DEFAULT_COMMENT;

    private String lineSeparator = DEFAULT_LINE_SEPARATOR;

    private CsvWriterFactory csvWriterFactory = DefaultCsvFactory.INSTANCE;

    private CsvReaderFactory csvReaderFactory = DefaultCsvFactory.INSTANCE;

    CsvContext() {
    }

    public CsvMarshaller<T> newMarshaller() {
        return new CsvMarshaller<T>(this, csvWriterFactory);
    }

    public CsvUnmarshaller<T> newUnmarshaller() {
        return new CsvUnmarshaller<T>(this, csvReaderFactory);
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
        if (converterService.canConvert(object.getClass(), String.class)) {
            return converterService.convert(object, String.class);
        }
        return "ERROR";
    }

    public <Q> Q unmarshall(Class<Q> clazz, String value) {
        if (converterService.canConvert(String.class, clazz)) {
            return converterService.convert(value, clazz);
        }
        return null;
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

    public String getLineSeparator() {
        return lineSeparator;
    }

    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    public ConversionService getConverterService() {
        return converterService;
    }

    public void setConverterService(ConversionService converterService) {
        this.converterService = converterService;
    }

    public void setCsvWriterFactory(CsvWriterFactory csvWriterFactory) {
        this.csvWriterFactory = csvWriterFactory;
    }

    public void setCsvReaderFactory(CsvReaderFactory csvReaderFactory) {
        this.csvReaderFactory = csvReaderFactory;
    }

}

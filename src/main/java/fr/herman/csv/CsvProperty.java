package fr.herman.csv;

public interface CsvProperty<T> {

    String getStringValue(CsvContext<T> context, T object);

    String getHeader();

    void setValue(CsvContext context, T object, String value);
}

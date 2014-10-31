package fr.herman.csv.mapper;

public interface CsvBeanProperties<T> {
    Class<T> getHandledClass();

    CsvProperty[] getProperties();

    T getNewInstance();
}

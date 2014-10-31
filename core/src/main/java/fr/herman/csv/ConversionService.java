package fr.herman.csv;

public interface ConversionService {
    boolean canConvert(Class<?> clazz1, Class<?> clazz2);

    <T> T convert(Object o, Class<?> clazz);
}

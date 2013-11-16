package fr.herman.csv.mapper;

import fr.herman.csv.CsvContext;

public interface ObjectToCsvMapper<T> {
    String[] toCsv(CsvContext<T> context, T object);

    String[] headersToCsv(CsvContext<T> context);
}

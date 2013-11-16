package fr.herman.csv.mapper;

import fr.herman.csv.CsvContext;

public interface CsvToObjectMapper<T> {
    T toObject(CsvContext<T> context, String[] values);

    void handleHeader(CsvContext<T> context, String[] headers);
}

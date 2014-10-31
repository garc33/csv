package fr.herman.csv.factories;

import java.io.InputStream;

import fr.herman.csv.CsvContext;
import fr.herman.csv.reader.CsvReader;

public interface CsvReaderFactory {
    CsvReader create(CsvContext<?> context, InputStream is);
}

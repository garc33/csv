package fr.herman.csv.reader;

import java.io.IOException;

public interface CsvReader {
    String[] readLine() throws IOException;
}

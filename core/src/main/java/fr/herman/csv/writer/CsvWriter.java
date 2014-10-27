package fr.herman.csv.writer;

import java.io.IOException;

public interface CsvWriter {
    void write(String[] values) throws IOException;

    void write(String value) throws IOException;

    void writeln() throws IOException;

    void flush() throws IOException;
}

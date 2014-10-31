package fr.herman.csv.writer;

import java.io.IOException;
import java.io.Writer;

public class SimpleCsvWriter implements CsvWriter {

    private final Writer writer;


    private boolean append = false;

    private final char   delimiter;
    private final String eol;

    public SimpleCsvWriter(Writer writer, char delimiter, String eol)
    {
        this.writer = writer;
        this.delimiter = delimiter;
        this.eol = eol;
    }

    @Override
    public void write(String[] values) throws IOException {
        for (String value : values) {
            write(value);
        }
    }

    @Override
    public void write(String value) throws IOException {
        if (append) {
            writer.write(delimiter);
        }
        if (value != null) {
            writer.write(value);
        }
        append = true;
    }

    @Override
    public void writeln() throws IOException {
        writer.write(eol);
        append = false;
    }

    @Override
    public void flush() throws IOException {
        writer.flush();
    }

}

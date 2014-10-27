package fr.herman.csv.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import fr.herman.csv.CsvContext;

public class SimpleCsvWriter implements CsvWriter {

    private final Writer writer;

    private final CsvContext context;

    private boolean append = false;

    public SimpleCsvWriter(CsvContext context, OutputStream os) {
        writer = new OutputStreamWriter(os);
        this.context = context;
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
            writer.append(context.getSeparator());
        }
        writer.write(value == null ? "" : value);
        append = true;
    }

    @Override
    public void writeln() throws IOException {
        writer.write(context.getLineSeparator());
        append = false;
    }

    @Override
    public void flush() throws IOException {
        writer.flush();
    }

}

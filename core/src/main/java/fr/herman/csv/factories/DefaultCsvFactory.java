package fr.herman.csv.factories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import fr.herman.csv.CsvContext;
import fr.herman.csv.reader.CsvReader;
import fr.herman.csv.reader.SimpleCsvReader;
import fr.herman.csv.writer.CsvWriter;
import fr.herman.csv.writer.SimpleCsvWriter;

public class DefaultCsvFactory implements CsvWriterFactory, CsvReaderFactory {

    public static final DefaultCsvFactory INSTANCE = new DefaultCsvFactory();

    @Override
    public CsvWriter create(CsvContext<?> context, OutputStream os) {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
        return new SimpleCsvWriter(writer, context.getSeparator(), context.getLineSeparator());
    }

    @Override
    public CsvReader create(CsvContext<?> context, InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        return new SimpleCsvReader(reader, context.getSeparator(), context.getQuote(), context.getComment());
    }

}

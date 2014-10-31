package fr.herman.csv.factories;

import java.io.OutputStream;

import fr.herman.csv.CsvContext;
import fr.herman.csv.writer.CsvWriter;

public interface CsvWriterFactory {
    CsvWriter create(CsvContext<?> context, OutputStream os);
}

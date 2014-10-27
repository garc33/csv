package fr.herman.csv;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import fr.herman.csv.exception.MarshallingException;
import fr.herman.csv.mapper.ObjectToCsvMapper;
import fr.herman.csv.writer.CsvWriter;
import fr.herman.csv.writer.SimpleCsvWriter;

public class CsvMarshaller<T> {

    private final CsvContext<T> context;

    CsvMarshaller(CsvContext<T> context) {
        this.context = context;
    }

    public void marshall(ObjectToCsvMapper<T> mapper, List<T> objects,
            OutputStream outputStream) throws MarshallingException {
        CsvWriter printer = new SimpleCsvWriter(context, outputStream);
        try {
            if (context.isWithHeader()) {
                String[] headers = mapper.headersToCsv(context);
                printer.write(headers);
                printer.writeln();
            }

            for (T object : objects) {
                String[] strings = mapper.toCsv(context, object);
                printer.write(strings);
                printer.writeln();
            }
            printer.flush();
        } catch (IOException ioe) {
            throw new MarshallingException(ioe);
        }
    }
}

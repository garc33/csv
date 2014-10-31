package fr.herman.csv;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import fr.herman.csv.exception.MarshallingException;
import fr.herman.csv.factories.CsvWriterFactory;
import fr.herman.csv.mapper.ObjectToCsvMapper;
import fr.herman.csv.writer.CsvWriter;

public class CsvMarshaller<T> {

    private final CsvContext<T> context;

    private final CsvWriterFactory factory;

    CsvMarshaller(CsvContext<T> context, CsvWriterFactory factory) {
        this.context = context;
        this.factory = factory;
    }

    public void marshall(ObjectToCsvMapper<T> mapper, Iterable<T> objects, OutputStream outputStream) throws MarshallingException {
        marshall(mapper, objects.iterator(), outputStream, false);
    }

    public void marshall(ObjectToCsvMapper<T> mapper, Iterable<T> objects, OutputStream outputStream, boolean append) throws MarshallingException {
        marshall(mapper, objects.iterator(), outputStream, append);
    }

    public void marshall(ObjectToCsvMapper<T> mapper, Iterator<T> iterator, OutputStream outputStream) throws MarshallingException {
        marshall(mapper, iterator, outputStream, false);
    }

    public void marshall(ObjectToCsvMapper<T> mapper, Iterator<T> iterator, OutputStream outputStream, boolean append) throws MarshallingException {
        CsvWriter printer = factory.create(context, outputStream);
        try {
            if (context.isWithHeader() && !append) {
                String[] headers = mapper.headersToCsv(context);
                printer.write(headers);
                printer.writeln();
            }

            while (iterator.hasNext()) {
                String[] strings = mapper.toCsv(context, iterator.next());
                printer.write(strings);
                printer.writeln();
            }
            printer.flush();
        } catch (IOException ioe) {
            throw new MarshallingException(ioe);
        }
    }
}

package fr.herman.csv;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import fr.herman.csv.exception.MarshallingException;
import fr.herman.csv.mapper.ObjectToCsvMapper;
import fr.herman.csv.writer.CsvWriter;
import fr.herman.csv.writer.SimpleCsvWriter;

public class CsvMarshaller<T> {

    private final CsvContext<T> context;

    CsvMarshaller(CsvContext<T> context) {
        this.context = context;
    }

    public void marshall(ObjectToCsvMapper<T> mapper, Iterable<T> objects, OutputStream outputStream) throws MarshallingException
    {
        marshall(mapper, objects.iterator(), outputStream);
    }

    public void marshall(ObjectToCsvMapper<T> mapper, Iterator<T> iterator, OutputStream outputStream) throws MarshallingException
    {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        CsvWriter printer = new SimpleCsvWriter(bufferedWriter, context.getSeparator(), context.getLineSeparator());
        try
        {
            if (context.isWithHeader())
            {
                String[] headers = mapper.headersToCsv(context);
                printer.write(headers);
                printer.writeln();
            }

            while (iterator.hasNext())
            {
                String[] strings = mapper.toCsv(context, iterator.next());
                printer.write(strings);
                printer.writeln();
            }
            printer.flush();
        }
        catch (IOException ioe)
        {
            throw new MarshallingException(ioe);
        }
    }
}

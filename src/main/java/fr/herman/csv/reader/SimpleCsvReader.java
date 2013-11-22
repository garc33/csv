package fr.herman.csv.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class SimpleCsvReader implements CsvReader {

    private final char delimiter, quote, comment;

    private final BufferedReader reader;

    private boolean isQuoted;

    public SimpleCsvReader(InputStream is, char delimiter, char quote,
            char comment) {
        this.delimiter = delimiter;
        this.quote = quote;
        this.comment = comment;
        reader = new BufferedReader(new InputStreamReader(is));
    }

    private String[] parseLine(String line) {
        List<String> tokens = new LinkedList<String>();
        int cursor = 0;
        int nextDelimiter;
        while (cursor < line.length()) {
            nextDelimiter = findNextDelimiter(cursor, line);
            String token = line.substring(cursor, nextDelimiter);
            cursor = nextDelimiter + 1;
            tokens.add(token);
        }

        // Handle delimiter at end of line
        cursor = line.length() - 1;
        if (!isQuoted && cursor > -1 && line.charAt(cursor) == delimiter) {
            tokens.add(null);
        }

        return tokens.toArray(new String[tokens.size()]);
    }

    private int findNextDelimiter(int cursor, String line) {
        while (cursor < line.length()) {
            char c = line.charAt(cursor);
            if (quote != c && delimiter != c) {
                cursor++;
            } else if (delimiter == c) {
                return cursor;
            } else {
                isQuoted = true;
                cursor = findNextIndexQuote(cursor, line) + 1;
            }
        }
        return line.length();
    }

    private int findNextIndexQuote(int cursor, String line) {
        int indexOf = line.indexOf(quote, cursor + 1);
        if (indexOf == -1) {
            return line.length();
        }
        isQuoted = false;
        return indexOf;
    }

    @Override
    public String[] readLine() throws IOException {
        String[] ret = null;
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.charAt(0) == comment) {
                continue;
            }
            ret = parseLine(line);
            break;
        }
        return ret;
    }

}

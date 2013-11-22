package fr.herman.csv;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import fr.herman.csv.reader.CsvReader;
import fr.herman.csv.reader.SimpleCsvReader;

@RunWith(DataProviderRunner.class)
public class ReaderTest {

    @DataProvider
    public static Object[][] dataProviderCsvReader() {
        return new Object[][]{
         // @formatter:off     
            {",,", 3, 0},
            {"a,a,a", 3, 1},
            {"aa,aa,aa", 3, 2},
            {"aaa,aaa,aaa", 3, 3},
            {"aaa,\",\",aaa", 3, 3},
            {"a,b,c,d", 4, 1},
            {"\",,,", 1, 4},
            {"\",,,\"", 1, 5},
            {"\",,,\",\",,,\"", 2, 5},
            {"#a,b,c\naa,bb\n#aaa,bbb,ccc,ddd", 2, 2}
         // @formatter:on
        };
    }

    @Test
    @UseDataProvider("dataProviderCsvReader")
    public void testCsvReader(String input, int nbTokens, int nbCharByToken) throws Throwable {
        ByteArrayInputStream is = new ByteArrayInputStream(input.getBytes());
        CsvReader parser = new SimpleCsvReader(is, ',', '"', '#');
        String[] line = parser.readLine();
        System.out.println(Arrays.toString(line));
        Assert.assertEquals(nbTokens, line.length);
        if (nbCharByToken > -1) {
            for (String token : line) {
                Assert.assertEquals(nbCharByToken, token.length());
            }
        }
    }

}

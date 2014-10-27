package fr.herman.csv;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import fr.herman.csv.reader.CsvReader;
import fr.herman.csv.reader.SimpleCsvReader;

public class ReaderTest {

    @DataProvider(name = "lines", parallel = true)
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

    @Test(dataProvider = "lines")
    public void testCsvReader(String input, int nbTokens, int nbCharByToken) throws Throwable {
        ByteArrayInputStream is = new ByteArrayInputStream(input.getBytes());
        CsvReader parser = new SimpleCsvReader(is, ',', '"', '#');
        String[] line = parser.readLine();
        System.out.println(Arrays.toString(line));
        assertThat(line).hasSize(nbTokens);
        if (nbCharByToken > -1) {
            for (String token : line) {
                Assert.assertEquals(nbCharByToken, token.length());
            }
        }
    }

}

package accessfintec.hiring.processors;

import accessfintec.hiring.data.StockRecord;
import com.sun.jndi.toolkit.url.UrlUtil;
import okhttp3.OkHttpClient;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;


import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

public class InputProcessorTest {
    private static OkHttpClient httpClient = new OkHttpClient.Builder().build();
    private static String testUrl =  "https://s3.amazonaws.com/test-data-samples/stocks.json";

    @Test
    public void testCsvProcessor() throws Exception {
        List<StockRecord> records = new CsvProcessor().process(
                getAbsolutePath("/stocks.csv"));
        Assert.assertEquals(records.size(), 3200);
    }

    @Test
    public void testJsonProcessor() throws Exception {
        List<StockRecord> records = new JsonFileProcessor().process(
                getAbsolutePath("/stocks.json"));
        Assert.assertEquals(records.size(), 3200);
    }

    @Ignore("This test should not be a part of build process because it issues request to a url")
    public void testUrlProcessor() throws Exception {
        List<StockRecord> records = new UrlSourceProcessor(httpClient).process(testUrl);
        Assert.assertEquals(records.size(), 3200);
    }

    private String getAbsolutePath(String resourceName) throws Exception {
        URL resource = CsvProcessor.class.getResource(resourceName);
        return Paths.get(resource.toURI()).toFile().getAbsolutePath();
    }
}

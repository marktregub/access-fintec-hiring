package accessfintec.hiring.processors;

import accessfintec.hiring.data.StockRecord;
import okhttp3.OkHttpClient;
import java.util.ArrayList;
import java.util.List;

public class InputProcessor {
    private static OkHttpClient httpClient = new OkHttpClient.Builder().build();
    public static List<StockRecord> process(String source) {
        List<StockRecord> res = new ArrayList<>();
        if(source.endsWith(".csv")) {
            res = new CsvProcessor().process(source);
        } else if(source.endsWith(".json")) {
            if(source.startsWith("http")) {
                res = new UrlSourceProcessor(httpClient).process(source);
            } else {
                res = new JsonFileProcessor().process(source);
            }
        } else {
            System.err.println("Unhandled source");
        }
        return res;
    }

}

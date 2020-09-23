package accessfintec.hiring.processors;

import accessfintec.hiring.data.StockRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.List;

public class UrlSourceProcessor extends IProcessor {
    private OkHttpClient httpClient;

    public UrlSourceProcessor(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public List<StockRecord> process(String sourceUrl) {
        List<StockRecord> result = new ArrayList<>();
        Request request = new Request.Builder()
                .url(sourceUrl).get().build();
        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
            result = processJsonString(response.body().string());
        } catch(Exception e) {
            System.err.println("Fail get request for url " + sourceUrl);
        } finally {
            if(response != null) {
                response.close();
            }
        }
        return result;
    }


}

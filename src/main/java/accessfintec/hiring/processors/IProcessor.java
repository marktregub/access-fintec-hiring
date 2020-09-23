package accessfintec.hiring.processors;

import accessfintec.hiring.data.StockRecord;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.util.ArrayList;
import java.util.List;

public abstract class IProcessor {
    abstract List<StockRecord> process(String source);

    List<StockRecord> processJsonString(String jsonString) throws Exception {
        JSONParser parser = new JSONParser();
        List<StockRecord> records = new ArrayList<>();

        JSONArray stocksArray = (JSONArray)parser.parse(jsonString);

        for(Object stockObject: stocksArray) {
            try {
                JSONObject stock = (JSONObject) stockObject;
                records.add(new StockRecord(stock.get("name"), stock.get("price")));
            } catch(Exception e) {
                System.err.println("Bad format for record " + stockObject.toString());
            }
        }
        return records;
    }
}

package accessfintec.hiring.processors;

import accessfintec.hiring.data.StockRecord;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonFileProcessor extends IProcessor {
    @Override
    public List<StockRecord> process(String jsonFile) {
        List<StockRecord> records = new ArrayList<>();

        try {
            records = processJsonString(new String(Files.readAllBytes(Paths.get(jsonFile))));
        } catch(Exception e) {
            System.err.println("JSON file read failure " + jsonFile);
        }

        return records;
    }


}

package accessfintec.hiring.processors;

import accessfintec.hiring.data.StockRecord;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CsvProcessor extends IProcessor {
    public List<StockRecord> process(String csvFile) {
        List<StockRecord> records = new ArrayList<>();
        try {
            CSVParser parser = CSVParser.parse(new File(csvFile), Charset.defaultCharset(), CSVFormat.DEFAULT.withFirstRecordAsHeader());
            for(CSVRecord record : parser) {
                try {
                    records.add(new StockRecord(record.get(0), record.get(2)));
                } catch(NumberFormatException e) {
                    System.err.println("Bad format for record " + record.toString());
                }
            }
        } catch(IOException e) {
            System.err.println("CSV file read failure: " + csvFile);
            e.printStackTrace();
        }
        return records;
    }
}

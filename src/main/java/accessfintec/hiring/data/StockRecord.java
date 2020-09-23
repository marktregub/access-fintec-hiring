package accessfintec.hiring.data;

import java.time.Instant;
import java.util.Map;

public class StockRecord {
    private String name;
    private Double price;

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public StockRecord(Object name, Object price) {
        this.name = String.valueOf(name);
        this.price = Double.parseDouble(String.valueOf(price));
    }

    public StockRecord(Map<String, String> data) {
        if(!data.containsKey("name") || !data.containsKey("price")) {
            throw new IllegalArgumentException("constructor map parameter must contain both 'name' and 'price' keys");
        }
        this.name = data.get("name");
        this.price = Double.parseDouble(data.get("price"));
    }
}

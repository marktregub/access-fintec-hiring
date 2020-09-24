package accessfintec.hiring;

import accessfintec.hiring.processors.InputProcessor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StocksProcessor {
    private static Map<String, Double> minimalPrices = new ConcurrentHashMap<>();
    private static Double totalMinPrice;

    private ScheduledExecutorService executorService;

    private int MAX_THREADS = 1000;
    private int EXECUTION_PERIOD_SECONDS = 20;

    public void parseSourcesPeriodically(String[] sources) {
        executorService = Executors.newScheduledThreadPool(Math.min(sources.length, MAX_THREADS));

        for(String source: sources) {
            executorService.scheduleAtFixedRate(() -> updateMinimalValue(source),
                    0, EXECUTION_PERIOD_SECONDS, TimeUnit.SECONDS);
        }
    }

    private void updateMinimalValue(String source) {
        System.out.println("Processing " + source);
        InputProcessor.process(source).forEach(stockRecord ->
                minimalPrices.compute(stockRecord.getName(), (stockName, stockPrice) -> {
                    Double currMin = minimalPrices.get(stockName);
                    return currMin == null || currMin > stockRecord.getPrice() ? stockRecord.getPrice() : currMin;
                })
        );
    }

//    private void updateMinimalValue(String source) {
//        System.out.println("Processing " + source);
//        InputProcessor.process(source).forEach(stockRecord -> {
//            Double currMin = minimalPrices.get(stockRecord.getName());
//            if(currMin == null || currMin > stockRecord.getPrice()) {
//                minimalPrices.put(stockRecord.getName(), stockRecord.getPrice());
//                if(totalMinPrice == null || stockRecord.getPrice() < totalMinPrice) {
//                    totalMinPrice = stockRecord.getPrice();
//                }
//            }
//        });
//    }

    public static String getLowestPrice(String stock) {
        Double minPrice = minimalPrices.get(stock);
        return minPrice == null? "stock not found" : minPrice.toString();
    }

    public static String getAllLowestPrices() {
        StringBuffer res = new StringBuffer();
        minimalPrices.entrySet().forEach(entry -> {
            res.append(entry.getKey()).append(" --> ").append(entry.getValue()).append("\n");
        });
        return res.toString();
    }
}

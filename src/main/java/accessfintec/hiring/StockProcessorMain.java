package accessfintec.hiring;

public class StockProcessorMain {
    public static void main(String[] args) {
        StocksProcessor stocksProcessor = new StocksProcessor();
        stocksProcessor.parseSourcesPeriodically(args);

        SocketService socketService = new SocketService();
        socketService.start();
    }
}

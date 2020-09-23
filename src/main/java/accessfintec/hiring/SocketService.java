package accessfintec.hiring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketService {
    public void start() {
        System.out.println("SocketServer started");
        ExecutorService executor = Executors.newCachedThreadPool();
        int port = 2424;
        ServerSocketChannel server = null;
        try {
            server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress(port));
            while (true){
                final SocketChannel socket = server.accept();
                // init new client;
                executor.submit(() -> {
                    BufferedReader reader = null;
                    PrintWriter writer = null;
                    try {
                        reader = new BufferedReader(Channels.newReader(socket, "utf-8"));
                        writer = new PrintWriter(Channels.newWriter(socket, "utf-8"));
                        while(socket.isConnected()){
                            String command = reader.readLine();
                            if(command == null) continue;

                            String output = executeCommand(command);
                            System.out.println("Command received: " + command + " output: " + output);

                            writer.println(output);
                            writer.flush();
                        }
                    }
                    catch (IOException ex){
                        if (!socket.isConnected()){
                            ex.printStackTrace();
                        }
                    } catch(IllegalArgumentException ex) {
                        writer.println("Bad input command");
                    }

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) try {server.close();} catch (Exception ex){}
        }
    }

    private String executeCommand(String command) {
        String errStr = "Usage: GetLowestPrice [stockName]| GetAllLowestPrices";
        if(command.startsWith("GetLowestPrice")) {
            String[] splittedCmd = command.split(" ");
            if(splittedCmd.length > 1) {
                return StocksProcessor.getLowestPrice(splittedCmd[1]);
            } else {
                return errStr;
            }
        } else if(command.startsWith("GetAllLowestPrices")) {
            return StocksProcessor.getAllLowestPrices();
        }
        else {
            return errStr;
        }
    }
}

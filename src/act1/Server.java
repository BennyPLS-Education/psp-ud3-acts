package act1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (var serverSocket = new ServerSocket(port)) {
            while (!serverSocket.isClosed()) {
                Socket client = serverSocket.accept();
                new Server.ClientHandler(client).start();
            }
        } catch (IOException e) {
            System.out.println("Error Server: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket client;

        public ClientHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            while (client.isConnected()) {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    PrintWriter out = new PrintWriter(client.getOutputStream());

                    String received = in.readLine();
                    if (received == null) {break;}

                    out.println(received.toUpperCase());
                    out.flush();
                } catch (IOException e) {
                    System.out.println("Error Server: " + e.getMessage());
                }
            }
        }
    }
}
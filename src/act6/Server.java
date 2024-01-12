package act6;

import javax.swing.plaf.TableHeaderUI;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private static final String relativePath = "src/act6/files/";
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (var serverSocket = new ServerSocket(port)) {
            while (!serverSocket.isClosed()) {
                Socket client = serverSocket.accept();
                new ClientHandler(client).start();
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

                    String fileName = in.readLine();
                    if (fileName == null) {return;}

                    StringBuilder content = new StringBuilder();
                    try (var fileReader = new BufferedReader(new FileReader(relativePath + fileName))) {
                        String line;
                        while ((line = fileReader.readLine()) != null) {
                            content.append(line).append("\n");
                        }

                        out.println(content);
                    } catch (FileNotFoundException e) {
                        out.println("File not found");
                    } catch (IOException ex) {
                        System.out.println("FileSystem Error: " + ex.getMessage());
                    }

                    out.flush();
                } catch (IOException e) {
                    System.out.println("Error Server: " + e.getMessage());
                }
            }
        }
    }
}
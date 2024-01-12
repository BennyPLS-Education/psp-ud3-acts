package act5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private final String relativePath = "src/act5/files/";
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (var serverSocket = new ServerSocket(port)) {
            Socket client = serverSocket.accept();

            while (client.isConnected()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream());

                String fileName = in.readLine();
                if (fileName == null) {break;}

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
            }
        } catch (IOException e) {
            System.out.println("Error Server: " + e.getMessage());
        }
    }
}
package act3;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server extends Thread {
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

                String received = in.readLine();
                if (received == null) {break;}

                String json = new JSONObject(received).toString(2);

                try (var FileWriter = new java.io.FileWriter("src/act3/addresses.txt")) {
                    FileWriter.write(json);
                } catch (IOException e) {
                    System.out.println("Error FileWriter: " + e.getMessage());
                }

                out.println("Ok");
                out.flush();
            }

        } catch (IOException e) {
            System.out.println("Error Server: " + e.getMessage());
        }
    }
}
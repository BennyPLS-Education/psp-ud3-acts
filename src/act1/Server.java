package act1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.OutputStream;
import java.io.PrintWriter;

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

                out.println(received.toUpperCase());
                out.flush();
            }

        } catch (IOException e) {
            System.out.println("Error Server");
        }
    }
}
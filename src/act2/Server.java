package act2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server extends Thread {
    private final int port;
    private final int secretNumber = new Random().nextInt(0, 101);

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

                int clientNumber;
                try {
                    clientNumber = Integer.parseInt(received);
                } catch (NumberFormatException e) {
                    out.println("Not a number");
                    out.flush();
                    continue;
                }

                if (clientNumber == secretNumber) {
                    out.println("Correct");
                } else if (clientNumber > secretNumber) {
                    out.println("Less");
                } else {
                    out.println("More");
                }

                out.flush();
            }

        } catch (IOException e) {
            System.out.println("Error Server");
        }
    }
}
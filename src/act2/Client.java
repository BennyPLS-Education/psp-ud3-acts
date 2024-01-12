package act2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
    private final String host;
    private final int port;


    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try (var socket = new Socket(host, port)) {
            String response = "";

            while (!response.equals("Correct")) {
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                System.out.print("Client text: ");
                out.println(new Scanner(System.in).nextLine());
                out.flush();

                response = in.readLine();
                System.out.println("The server responded with: " + response);
            }

        } catch (IOException e) {
            System.out.println("Error Client: " + e.getMessage());
        }
    }
}
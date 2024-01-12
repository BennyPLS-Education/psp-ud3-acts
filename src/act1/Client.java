package act1;

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
        System.out.print("Client text: ");
        String text = new Scanner(System.in).nextLine();

        try (var socket = new Socket(host, port)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(text);
            out.flush();

            System.out.println("The result is: " + in.readLine());
        } catch (IOException e) {
            System.out.println("Error Client: " + e.getMessage());
        }
    }
}
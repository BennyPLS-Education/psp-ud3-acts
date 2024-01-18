package act1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static act1.Main.HOST;
import static act1.Main.PORT;

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
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                System.out.print("Client text: ");
                out.println(new Scanner(System.in).nextLine());
                out.flush();

                System.out.println("The result is: " + in.readLine());
            }

        } catch (IOException e) {
            System.out.println("Error Client: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Client client = new Client(HOST, PORT);

        client.start();
    }
}
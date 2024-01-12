package act4;

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


            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            try (var scanner = new Scanner(System.in)) {
                System.out.print("Operation (add, sub, mul, div): ");
                out.println(scanner.nextLine());
                System.out.print("Number 1: ");
                out.println(scanner.nextLine());
                System.out.print("Number 2: ");
                out.println(scanner.nextLine());
                out.flush();
            }

            var response = in.readLine();
            System.out.println("The server responded with: " + response);


        } catch (IOException e) {
            System.out.println("Error Client: " + e.getMessage());
        }
    }
}
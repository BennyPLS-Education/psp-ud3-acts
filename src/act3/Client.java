package act3;

import org.json.JSONObject;

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
        JSONObject json = new JSONObject();
        try (var socket = new Socket(host, port)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            try (var scanner = new Scanner(System.in)) {
                System.out.print("Address: ");
                json.put("address", scanner.nextLine());

                System.out.print("Postal Code: ");
                json.put("postalCode", scanner.nextLine());

                System.out.print("City: ");
                json.put("city", scanner.nextLine());

                System.out.print("Country: ");
                json.put("houseNumber", scanner.nextLine());
            }

            out.println(json);
            out.flush();

            var response = in.readLine();
            System.out.println("The server responded with: " + response);
        } catch (IOException e) {
            System.out.println("Error Client: " + e.getMessage());
        }
    }
}
package act6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static act6.Main.HOST;
import static act6.Main.PORT;

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
            String response = "File not found";

            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (response.equals("File not found")) {
                String fileName;

                System.out.print("Enter the name of a file: ");
                fileName = new Scanner(System.in).nextLine();


                out.println(fileName);
                out.flush();

                response = in.readLine();

                if (response.equals("File not found")) {
                    System.out.println(response);
                    continue;
                }

                System.out.println("File Contents");
                System.out.println(response);
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
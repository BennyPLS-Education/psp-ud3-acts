package act4;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void run() {
        try (var serverSocket = new ServerSocket(port)) {
            while (!serverSocket.isClosed()) {
                Socket client = serverSocket.accept();
                new Server.ClientHandler(client).start();
            }
        } catch (IOException e) {
            System.out.println("Error Server: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket client;

        public ClientHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            while (client.isConnected()) {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    PrintWriter out = new PrintWriter(client.getOutputStream());

                    String operation = in.readLine();
                    String number1 = in.readLine();
                    String number2 = in.readLine();
                    if (operation == null || number1 == null || number2 == null) {break;}

                    int num1;
                    int num2;

                    try {
                        num1 = Integer.parseInt(number1);
                        num2 = Integer.parseInt(number2);
                    } catch (NumberFormatException e) {
                        out.println("Not a number");
                        out.flush();
                        continue;
                    }

                    switch (operation.trim().toLowerCase()) {
                        case "add" -> out.println(num1 + num2);
                        case "sub" -> out.println(num1 - num2);
                        case "mul" -> out.println(num1 * num2);
                        case "div" -> {
                            if (num2 == 0) {
                                out.println(num1 > 0 ? "" : "-" + "Infinity");
                            } else {
                                out.println(num1 / num2);
                            }
                        }
                        default -> out.println("Invalid operation " + operation);
                    }

                    out.flush();
                } catch (IOException e) {
                    System.out.println("Error Server: " + e.getMessage());
                }
            }
        }
    }
}
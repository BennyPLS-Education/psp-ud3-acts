package act5;

import act1.Client;

public class Main {
    public final static int PORT = 1500;
    public final static String HOST = "localhost";

    public static void main(String[] args) {
        Server server = new Server(PORT);
        Client client = new Client(HOST, PORT);

        server.start();
        client.start();
        System.out.println("Server started on port " + PORT);
    }
}

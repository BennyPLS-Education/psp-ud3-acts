package act1;

public class Main {
    private final static int PORT = 2222;
    private final static String HOST = "localhost";

    public static void main(String[] args) {
        Server server = new Server(PORT);
        Client client = new Client(HOST, PORT);

        server.start();
        client.start();
    }
}

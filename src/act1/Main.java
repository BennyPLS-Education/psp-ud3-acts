package act1;

public class Main {
    public final static int PORT = 2222;
    public final static String HOST = "localhost";

    public static void main(String[] args) {
        Server server = new Server(PORT);
        Client client = new Client(HOST, PORT);

        server.start();
        client.start();
    }
}

package act4;

public class Main {
    public final static int PORT = 2000;
    public final static String HOST = "localhost";

    public static void main(String[] args) {
        Server server = new Server(PORT);
        Client client = new Client(HOST, PORT);

        server.start();
        client.start();
    }
}

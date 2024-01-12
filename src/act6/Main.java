package act6;

public class Main {
    public final static int PORT = 1500;
    public final static String HOST = "localhost";

    public static void main(String[] args) {
        Server server = new Server(PORT);

        server.start();
        System.out.println("Server started on port " + PORT);
    }
}

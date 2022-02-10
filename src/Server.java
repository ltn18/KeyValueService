import java.io.*;
import java.net.*;

public class Server {
    private final int PORT = 8080;
    private ServerSocket serverSocket;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private KeyValueStore kvs;

    public void initServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sendResponseToClient(String msg) {
        if (msg.equals("help")) {
            out.println("Call help");
        }
    }

    public void closeServer() throws IOException {
        in.close();
        out.close();
        serverSocket.close();
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.initServer(server.PORT);

        boolean open = true;

        while (open) {
            String msg = server.in.readLine();
            server.sendResponseToClient(msg);
            if (msg.equals("bye")) open = false;
        }

        KeyValueStore kvs = new KeyValueStore();
        kvs.put("OK", "1");
        kvs.put("OKOK", "2");

        server.closeServer();
    }
}

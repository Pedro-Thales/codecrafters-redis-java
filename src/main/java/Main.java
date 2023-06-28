import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.out.println("Logs from your program will appear here!");
        //  Uncomment this block to pass the first stage
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        int port = 6379;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            // Wait for connection from client.
            clientSocket = serverSocket.accept();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());

            while (true) {
                String s = br.readLine();

                if (s == null) {
                    continue;
                }

                if (s.startsWith("*") || s.startsWith("$")) {
                    continue;
                }

                switch (s.toLowerCase(Locale.ROOT)) {
                    case "ping":
                        pw.write("+PONG\r\n");
                        pw.flush();
                    case "exit":
                        break;
                    default:
                        pw.write("+INVALID COMMAND\r\n");
                        pw.flush();
                }

            }


        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }
}

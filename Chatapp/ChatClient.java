import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 12345;

        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.print("Enter your username: ");
            String userName = input.readLine();
            out.println(userName + " joined the chat.");

            Thread listener = new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        System.out.println(response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            listener.start();

            String message;
            while ((message = input.readLine()) != null) {
                out.println(userName + ": " + message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

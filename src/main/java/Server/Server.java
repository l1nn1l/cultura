package Server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public static void main(String[] args) throws IOException {

       /* if ((args.length) < 1){
            System.exit(-1);
        }

        int port = Integer.parseInt(args[0]);*/

        ServerSocket serverSocket = new ServerSocket(5056);
        System.out.println("server is running");

        // Server keeps on receiving new Clients
        while (true) {
            Socket clientSocket = null;
            try {
                // ServerSocket waits for a Client to connect
                clientSocket = serverSocket.accept();

                System.out.println("A new client is connected : " + clientSocket);

                // Receiving input and sending output to Client
                ObjectInputStream inputFromClient = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream outputToClient = new ObjectOutputStream(clientSocket.getOutputStream());

                // Create a new Thread object for the Client
                Thread thread = new ClientHandler(clientSocket, inputFromClient, outputToClient);
                thread.start();

            } catch (Exception e) {
                clientSocket.close();
                e.printStackTrace();
            }
        }
    }
}

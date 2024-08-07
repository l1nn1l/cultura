package Server;
import com.cultura.ActiveClientsManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {

        System.out.println(args.length);
        if ((args.length) < 1){
            System.out.println("enter the port number!");
            System.exit(-1);
        }

        int port = Integer.parseInt(args[0]);

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("server is running");
        System.out.println(port + " number");

        ActiveClientsManager activeClientsManager = ActiveClientsManager.getInstance();
        activeClientsManager.initActiveClientsSet();
        System.out.println("done " +activeClientsManager);
        // Server keeps on receiving new Clients
        while (true) {
            Socket clientSocket = null;
            try {
                // ServerSocket waits for a Client to connect
                clientSocket = serverSocket.accept();

                // Receiving input and sending output to Client
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();
                System.out.println("got the streams all good " + (inputStream == null)
                        + " " + (outputStream == null));
                ObjectOutputStream outputToClient = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inputFromClient = new ObjectInputStream(clientSocket.getInputStream());
                System.out.println("the sockets were created " + (inputFromClient == null) + " ");

                activeClientsManager.addActiveClient(clientSocket,outputToClient);
                System.out.println("A new client is connected : " + clientSocket);
                System.out.println("socket list is " + activeClientsManager.getActiveClientsSet());

                // Create a new Thread object for the Client
                Thread thread = new ClientHandler(clientSocket, inputFromClient, outputToClient);
                thread.start();

            } catch (Exception e) {
                System.out.println("this is not working");
                clientSocket.close();
                e.printStackTrace();
            }
        }
    }
}

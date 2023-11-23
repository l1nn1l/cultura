package Server;
import com.cultura.Requests.LoginRequest;
import com.cultura.Requests.SignupRequest;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    final Socket clientSocket;
    final ObjectInputStream inputFromClient;
    final ObjectOutputStream outputToClient;

    // Constructor
    public ClientHandler(Socket clientSocket, ObjectInputStream inputFromClient, ObjectOutputStream outputToClient) {
        this.clientSocket = clientSocket;
        this.inputFromClient = inputFromClient;
        this.outputToClient = outputToClient;
    }

    @Override
    public void run() {

        Object received;
        Object toreturn;

        while (true) {
            try {

                // Receive the answer from Client
                received = inputFromClient.readObject();

                System.out.println("received was " + received);

                if (received instanceof String){
                    if (received.equals("Exit")) {
                        System.out.println("-----------------------------------------------------------------------------------");
                        System.out.println("Client " + this.clientSocket + " sends exit...");
                        System.out.println("Closing this connection.");
                        this.clientSocket.close();
                        System.out.println("Connection closed");
                        break;
                    }
                }
                else if (received instanceof SignupRequest){
                    System.out.println("Client " + this.clientSocket + " is signing up");
                    SignupRequest signupRequest = (SignupRequest) received;
                    String name = signupRequest.name;
                    String username = signupRequest.username;
                    String password = signupRequest.password;
                    String email = signupRequest.email;
                    UserFunctions.signupUser(username, password, name, email);
                    outputToClient.writeUTF("Signup successful");
                }
                else if (received instanceof LoginRequest) {
                    System.out.println("Client " + this.clientSocket + " is logging in");
                    LoginRequest loginRequest = (LoginRequest) received;
                    String username = loginRequest.username;
                    String password = loginRequest.password;
                    UserFunctions.loginUser(username, password);
                    outputToClient.writeUTF("Login successful");
                    break;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            // Closing resources
            this.inputFromClient.close();
            this.outputToClient.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
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
        this.outputToClient = outputToClient;
        this.inputFromClient = inputFromClient;
    }

    @Override
    public void run() {

        Object received;
        Object toreturn;

       while (true) {
            try {
                received = inputFromClient.readObject();
                System.out.println("we got the response");
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
                    else {
                        System.out.println("client says " + received);
                    }
                }
                else if (received instanceof SignupRequest){
                    System.out.println("Client " + this.clientSocket + " is signing up");
                    SignupRequest signupRequest = (SignupRequest) received;
                    String name = signupRequest.name;
                    String username = signupRequest.username;
                    String password = signupRequest.password;
                    String email = signupRequest.email;
                    boolean worked = UserFunctions.signupUser(username, password, name, email);
                    if (worked) {
                        outputToClient.writeObject("Signup successful");
                    } else {
                        outputToClient.writeObject("Signup failed");
                    }


                }
                else if (received instanceof LoginRequest) {
                    System.out.println("Client " + this.clientSocket + " is logging in");
                    LoginRequest loginRequest = (LoginRequest) received;
                    String username = loginRequest.username;
                    String password = loginRequest.password;
                    boolean worked = UserFunctions.loginUser(username, password);
                    if (worked) {
                        outputToClient.writeObject("Login successful");
                    } else {
                        outputToClient.writeObject("Login failed");
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                try {
                    outputToClient.writeObject("Request failed. Please try again.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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
package com.cultura;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    public Socket socket;
    public ObjectInputStream inputFromServer;
    public ObjectOutputStream outputToServer;

    public Client () throws IOException {

        System.out.println(1);
        // Getting local IP Address (127.0.0.1)
        InetAddress ip = InetAddress.getByName("localhost");
        System.out.println(2);
        // Establish the connection with Server on port 5056
        try {
            System.out.println("client is running");
            socket = new Socket(ip, 5056);
            System.out.println("client finished running");
            System.out.println(socket == null);
            inputFromServer = new ObjectInputStream(socket.getInputStream());
            System.out.println(3);
            outputToServer = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e){
                System.out.println("there was an error");
                e.printStackTrace();
            }
    }

    public void sendRequest(Object request) throws IOException {
        outputToServer.writeObject(request);
    }

    public static void main(String[] args) throws IOException {
        System.out.println("inside client");
        try {
            App.launch();
            Client client = new Client();
            System.out.println("this is running!!!");

            // signup
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("signup.fxml"));
            Parent signupRoot = fxmlLoader.load();
            SignupController signupController = fxmlLoader.getController();
            signupController.setClient(client);

            // login
            fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
            Parent loginRoot = fxmlLoader.load();
            LoginController loginController = fxmlLoader.getController();
            loginController.setClient(client);

            Scanner scan = new Scanner(System.in);

            // This will trigger the accept() function of the Server

            // Receiving input and sending output to Server


            while (true) {

                System.out.println("hi");//inputFromServer.readUTF());
                String tosend = scan.nextLine();
            //    outputToServer.writeUTF(tosend);

                // Sending Exit closes the connection and breaks the loop
                if(tosend.equals("Exit"))
                {
                    System.out.println("-----------------------------------------------------------------------------------");
             //       System.out.println("Closing this connection : " + socket);
               //     socket.close();
                    System.out.println("Connection closed");
                    break;
                }
                System.out.println("-----------------------------------------------------------------------------------");
                // Printing message received from Server
                String received = "hi";//inputFromServer.readUTF();
                System.out.println(received);
            }

            // Closing resources
            scan.close();
         //   inputFromServer.close();
          //  outputToServer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

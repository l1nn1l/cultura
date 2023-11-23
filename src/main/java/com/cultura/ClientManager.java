package com.cultura;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientManager {
    private Client client;

    public void initClient() {
        try {
            System.out.println("Inside ClientManager");
            client = new Client();
            System.out.println("Client is running!!!");

            Scanner scan = new Scanner(System.in);

            /*while (true) {
                System.out.println("hi");
                String toSend = scan.nextLine();
                // client.outputToServer.writeUTF(toSend);

                if (toSend.equals("Exit")) {
                    System.out.println("Connection closed");
                    break;
                }

                System.out.println("-----------------------------------------------------------------------------------");
                String received = "hi"; // client.inputFromServer.readUTF();
                System.out.println(received);
            }*/

            // Closing resources
            scan.close();
            // client.inputFromServer.close();
            // client.outputToServer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Client getClient() {
        return client;
    }
}

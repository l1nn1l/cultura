package com.cultura;

import java.util.Scanner;

public class ClientManager {
    private static ClientManager instance;
    private Client client;

    private ClientManager() {}

    public static synchronized ClientManager getInstance() {
        if (instance == null) {
            instance = new ClientManager();
        }
        return instance;
    }

    public void initClient() {
        try {
            System.out.println("Inside ClientManager");
            client = new Client();
            System.out.println("Client is running!!!");

            // Closing resources
            /*scan.close();
            client.inputFromServer.close();
            client.outputToServer.close();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Client getClient() {
        return client;
    }
}

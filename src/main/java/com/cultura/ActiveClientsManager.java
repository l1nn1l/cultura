package com.cultura;

import java.net.Socket;
import java.util.HashSet;

public class ActiveClientsManager {
    private static ActiveClientsManager instance;
    private HashSet<Socket> activeClients;

    private ActiveClientsManager() {}

    public static synchronized ActiveClientsManager getInstance() {
        if (instance == null) {
            instance = new ActiveClientsManager();
        }
        return instance;
    }

    public void initActiveClientsSet() {
        try {
            System.out.println("Inside active clients");
            activeClients = new HashSet<>();
            System.out.println("active clients created!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized HashSet<Socket> getActiveClients() {
        return activeClients;
    }

    public synchronized HashSet<Socket> addActiveClient(Socket clientSocket) {
        activeClients.add(clientSocket);
        return activeClients;
    }

    public HashSet<Socket> removeActiveClient(Socket clientSocket) {
        activeClients.remove(clientSocket);
        return activeClients;
    }
}

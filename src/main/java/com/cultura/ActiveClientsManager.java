package com.cultura;

import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;

public class ActiveClientsManager {
    private static ActiveClientsManager instance;
    private HashSet<Socket> activeClients;
    private HashMap<Socket, ObjectOutputStream> activeClientsOutputStreams;
    private ActiveClientsManager() {
        activeClients = new HashSet<>();
        activeClientsOutputStreams = new HashMap<>();
    }

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
            activeClientsOutputStreams = new HashMap<>();
            System.out.println("active clients created!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized HashSet<Socket> getActiveClientsSet() {
        return activeClients;
    }

    public synchronized HashMap<Socket, ObjectOutputStream> getActiveClientsMap() {
        return activeClientsOutputStreams;
    }

    public synchronized HashSet<Socket> addActiveClient(Socket clientSocket, ObjectOutputStream objectOutputStream) {
        activeClients.add(clientSocket);
        activeClientsOutputStreams.put(clientSocket, objectOutputStream);
        return activeClients;
    }

    public HashSet<Socket> removeActiveClient(Socket clientSocket) {
        activeClients.remove(clientSocket);
        activeClientsOutputStreams.remove(clientSocket);
        return activeClients;
    }
}

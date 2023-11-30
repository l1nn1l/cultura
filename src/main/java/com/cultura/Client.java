package com.cultura;

import com.cultura.Requests.GetFollowersRequest;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Client {

    public Socket socket;
    public ObjectInputStream inputFromServer;
    public ObjectOutputStream outputToServer;
    public String username;

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
            outputToServer = new ObjectOutputStream(socket.getOutputStream());
            inputFromServer = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e){
                System.out.println("there was an error");
                e.printStackTrace();
            }
    }

    public Object sendRequest(Object request) throws IOException, ClassNotFoundException {
        System.out.println("sending the request");
        outputToServer.writeObject(request);
        System.out.println("sending the request...");
        Object response = inputFromServer.readObject();
        System.out.println("response was" + response);

        if (response instanceof BroadCastPostResponse){
            System.out.println("broadcastin obj");
            BroadCastPostResponse broadCastPostResponse = (BroadCastPostResponse) response;
            Controllers.timelineController.addBroadCastedPost(broadCastPostResponse.tweet);
            response = inputFromServer.readObject();
        }

        return response;
    }


}

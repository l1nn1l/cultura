package Server;
import com.cultura.*;
import com.cultura.Requests.*;
import com.cultura.CreateDB;
import com.cultura.Requests.GetComments;
import com.cultura.Requests.GetFollowersPostRequest;
import com.cultura.Requests.GetUsersPostsRequest;
import com.cultura.Requests.LoginRequest;
import com.cultura.Requests.MakeCommentRequest;
import com.cultura.Requests.MakePostRequest;
import com.cultura.Requests.SignupRequest;
import com.cultura.Tweet;
import com.cultura.TweetComments;
import com.cultura.objects.Post;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashSet;

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
                // receive requests
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
                else if (received instanceof MakePostRequest){
                    System.out.println("Client " + this.clientSocket + " is posting a tweet");
                    MakePostRequest postRequest = (MakePostRequest) received;
                    String username = postRequest.username;
                    String postTweet = postRequest.postText;
                    boolean worked = UserFunctions.postTweet(username, postTweet);
                    if (worked){
                        outputToClient.writeObject("Posted successfully");
                        // broadcast to the others!!!
                    } else {
                        outputToClient.writeObject("Post unsuccessful");
                    }
                } else if (received instanceof GetUsersPostsRequest){
                    System.out.println("Client " + this.clientSocket + " is getting their posts");
                    GetUsersPostsRequest getUsersPostsRequest = (GetUsersPostsRequest) received;
                    String username = getUsersPostsRequest.username;
                    ArrayList<Tweet> userPosts = CreateDB.getUserTweets(username);
                    outputToClient.writeObject(userPosts);
                }
                else if (received instanceof GetFollowersPostRequest){
                    System.out.println("Client " + this.clientSocket + " is getting his follower's tweets");
                    GetFollowersPostRequest GetFollowersPostRequest = (GetFollowersPostRequest) received;
                    String username = GetFollowersPostRequest.username;
                    ArrayList<Tweet> followersPosts = CreateDB.getTweetsYouFollow(username);
                    outputToClient.writeObject(followersPosts);
                }
                else if (received instanceof FollowRequest) {
                    System.out.println("Client " + this.clientSocket + " is sending a follow");
                    FollowRequest followRequest = (FollowRequest) received;
                    Follow follow = followRequest.follow;
                    boolean followWorked = CreateDB.addFollow(follow);
                    outputToClient.writeObject(followWorked ? "Follow added successfully" :
                            "Follow unsuccessful");
                }
                else if (received instanceof GetAllUsersRequest) {
                    System.out.println("Client " + this.clientSocket + " is requesting all usernames");
                    ArrayList<String> allUsers = CreateDB.getAllUsernames();
                    outputToClient.writeObject(allUsers);
                }
                else if (received instanceof GetFollowersRequest){
                    System.out.println("Client " + this.clientSocket + " is requesting all followers");
                    GetFollowersRequest getFollowersRequest = (GetFollowersRequest) received;
                    ArrayList<String> followedUsers = CreateDB.getFollowsOfUser(getFollowersRequest.username);
                    outputToClient.writeObject(followedUsers);
                 }

                else if (received instanceof MakeCommentRequest){
                    System.out.println("Client " + this.clientSocket + " is commenting on a tweet");
                    MakeCommentRequest commentRequest = (MakeCommentRequest) received;
                    int tweetId = commentRequest.tweetId;
                    String username = commentRequest.username;
                    String comment = commentRequest.commentText;
                    boolean worked = UserFunctions.postComment(tweetId, username, comment);
                    if (worked){
                        outputToClient.writeObject("Comment posted successfully");
                    } else {
                        outputToClient.writeObject("Comment post unsuccessful");
                    }
                }
                else if (received instanceof GetComments){
                    System.out.println("Client " + this.clientSocket + " is getting comments on post");
                    GetComments getCommentsRequest = (GetComments) received;
                    int tweetId = getCommentsRequest.tweetId;
                    ArrayList<TweetComments> comments = CreateDB.getTweetComments(tweetId);
                    outputToClient.writeObject(comments);
                }


                else {
                    System.out.println("received something weird " + received);
                    outputToClient.writeObject("received something weird");
                }

              
            } catch (SocketException e) {
                ActiveClientsManager activeClientsManager = ActiveClientsManager.getInstance();
                activeClientsManager.removeActiveClient(clientSocket);
                // Closing resources
                Client client = ClientManager.getInstance().getClient();
                System.out.println("Client has disconnected");
                break;
                /*try {
                    client.inputFromServer.close();
                    client.outputToServer.close();
                    System.out.println("Client has disconnected");
                    break;
                } catch (IOException ex) {
                    ex.printStackTrace();
                    break;
                }*/
            }
            catch (IOException | ClassNotFoundException e) {
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
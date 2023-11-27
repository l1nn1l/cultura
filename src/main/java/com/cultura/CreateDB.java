package com.cultura;
import java.sql.*;
import java.util.ArrayList;

import com.cultura.objects.Reactions;


public class CreateDB {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "cultura_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "rawan123";

    public static void main(String[] args) {
        createDB();
        createUsersTable();
        createTweetsTable();
        createCommentsTable();
        createReactionsTable();
        //Tweet tweet = new Tweet("testinggg from linn !", "linn");
        //addTweet(tweet);
        //System.out.println(tweet);

        createFollowsTable();

        //System.out.print(getTweets());

        //System.out.println(getUserTweets("rawan"));

        //new Follow("user1", "user2");

        //System.out.println(getFollowsOfUser("user1"));

        //System.out.println(getTweetsYouFollow("rawan"));

        TweetComments comment = new TweetComments(1, "sara", "nice post rawan !");
        //addTweetComment(comment);

        //System.out.println(getTweetComments(8));

        Reactions reaction = new Reactions(1,"linn", "love");
        System.out.println(addReaction(reaction));

    }

    private static boolean createDB(){

        Connection connection = null;
        Statement statement = null;

        try {

            connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            statement = connection.createStatement();
            String sql = "CREATE DATABASE cultura_db";
            statement.executeUpdate(sql);
            System.out.println("Database created successfully");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // Closing resources
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static boolean createUsersTable(){

        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(JDBC_URL+DB_NAME, DB_USER, DB_PASSWORD);

            statement = connection.createStatement();
            String createTableSQL = "CREATE TABLE user " +
                    "(username VARCHAR(255) NOT NULL PRIMARY KEY, " +
                    "password VARCHAR(255) NOT NULL, " +
                    "name VARCHAR(255), " +
                    "email VARCHAR(255))"
                    ;
            statement.executeUpdate(createTableSQL);

            System.out.println("Table 'user' created successfully");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean insertUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertUserSQL = "INSERT INTO user (username, password, name, email) VALUES (?, ?, ?, ?)";

        try {
            connection = DriverManager.getConnection(JDBC_URL + DB_NAME, DB_USER, DB_PASSWORD);

            preparedStatement = connection.prepareStatement(insertUserSQL);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getEmail());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private static boolean createTweetsTable(){
        Connection connection = null;
        Statement statement = null;
        try{

            connection = DriverManager.getConnection(JDBC_URL+DB_NAME, DB_USER, DB_PASSWORD);

            statement = connection.createStatement();

            String createTweetTableSQL = "CREATE TABLE tweets (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "username VARCHAR(255) NOT NULL," +
                    "text VARCHAR(255) NOT NULL," +
                    "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "likeCount INT DEFAULT 0," +
                    "FOREIGN KEY (username) REFERENCES user(username)" +
                    ")";


            statement.executeUpdate(createTweetTableSQL);

            System.out.println("Table 'tweets' created successfully");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean addTweet(Tweet tweet) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
    
        String insertTweetSQL = "INSERT INTO tweets (username, text) VALUES (?, ?)";
    
        try {
            connection = DriverManager.getConnection(JDBC_URL + DB_NAME, DB_USER, DB_PASSWORD);
    
            preparedStatement = connection.prepareStatement(insertTweetSQL, Statement.RETURN_GENERATED_KEYS);
    
            preparedStatement.setString(1, tweet.getUsername());
            preparedStatement.setString(2, tweet.getText());
    
            int rowsAffected = preparedStatement.executeUpdate();
    
            if (rowsAffected > 0) {
                // Retrieve the generated keys (tweet ID)
                generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int tweetId = generatedKeys.getInt(1);
                    tweet.setTweetId(tweetId);
                    System.out.println("Tweet added successfully. Tweet ID: " + tweetId);
                }
            }
    
            return rowsAffected > 0;
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

    public static ArrayList<Tweet> getTweets() {
        ArrayList<Tweet> tweets = new ArrayList<>();

        try {


            Connection connection = DriverManager.getConnection(JDBC_URL + DB_NAME, DB_USER, DB_PASSWORD);

            Statement statement = connection.createStatement();

            ResultSet results = statement.executeQuery("SELECT * FROM tweets");

            while (results.next()) {
                int id = results.getInt("id");
                String text = results.getString("text");
                Timestamp timestamp = results.getTimestamp("timestamp");
                String username = results.getString("username");
                int like_count = results.getInt("likeCount");

                Tweet tweet = new Tweet(id, text, timestamp, username, like_count);
                tweets.add(tweet);
            }

            results.close();
            statement.close();
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return tweets;
    }


    public static ArrayList<Tweet> getUserTweets(String username) {
        ArrayList<Tweet> tweets = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL + DB_NAME, DB_USER, DB_PASSWORD)) {

            String query = "SELECT * FROM tweets WHERE username = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);

                try (ResultSet results = statement.executeQuery()) {
                    while (results.next()) {
                        int id = results.getInt("id");
                        String text = results.getString("text");
                        Timestamp timestamp = results.getTimestamp("timestamp");
                        int like_count = results.getInt("likeCount");

                        Tweet tweet = new Tweet(id, text, timestamp, username, like_count);
                        tweets.add(tweet);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tweets;
    }


    // table showing who is following who
    public static boolean createFollowsTable() {
        Connection connection = null;
        Statement statement = null;
        try{

            connection = DriverManager.getConnection(JDBC_URL+DB_NAME, DB_USER, DB_PASSWORD);

            statement = connection.createStatement();

            String createFollowsTableSQL = "CREATE TABLE follows (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "followed_by_username VARCHAR(255)," +
                    "following_username VARCHAR(255)," +
                    "FOREIGN KEY (followed_by_username) REFERENCES user(username)," +
                    "FOREIGN KEY (following_username) REFERENCES user(username)" +
                    ")";


            statement.executeUpdate(createFollowsTableSQL);

            System.out.println("Table 'follows' created successfully");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public static boolean addFollow(Follow follow) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String addFollowSQL = "INSERT INTO follows (followed_by_username, following_username) VALUES (?, ?)";

        try {
            connection = DriverManager.getConnection(JDBC_URL + DB_NAME, DB_USER, DB_PASSWORD);

            preparedStatement = connection.prepareStatement(addFollowSQL);

            preparedStatement.setString(1, follow.getFollowed_by_username());
            preparedStatement.setString(2, follow.getFollowing_username());

            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println("Follow added successfully.");

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<String> getFollowsOfUser(String username) {
        ArrayList<String> FollowsOfUser = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(JDBC_URL + DB_NAME, DB_USER, DB_PASSWORD);

            String getFollowsQueries = "SELECT following_username FROM follows WHERE followed_by_username = ?";

            PreparedStatement statement = connection.prepareStatement(getFollowsQueries);

            statement.setString(1, username);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                String followingUsername = results.getString("following_username");
                FollowsOfUser.add(followingUsername);
            }

            results.close();
            statement.close();
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return FollowsOfUser;
    }


    public static ArrayList<Tweet> getTweetsYouFollow(String username) {
        ArrayList<Tweet> tweets = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(JDBC_URL + DB_NAME, DB_USER, DB_PASSWORD);

            String query = "SELECT * FROM tweets "
                    + "INNER JOIN follows ON follows.following_username = tweets.username "
                    + "WHERE follows.followed_by_username = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);

                try (ResultSet results = statement.executeQuery()) {
                    while (results.next()) {
                        int id = results.getInt("id");
                        String text = results.getString("text");
                        Timestamp timestamp = results.getTimestamp("timestamp");
                        String tweetUsername = results.getString("username");
                        int likeCount = results.getInt("likeCount");

                        Tweet tweet = new Tweet(id, text, timestamp, tweetUsername, likeCount);
                        tweets.add(tweet);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return tweets;
    }

    public static ArrayList<String> getAllUsernames() {
        ArrayList<String> usernames = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(JDBC_URL + DB_NAME, DB_USER, DB_PASSWORD);

            String query = "SELECT username FROM user";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet results = statement.executeQuery();

                while (results.next()) {
                    String username = results.getString("username");
                    usernames.add(username);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usernames;
    }
    private static boolean createCommentsTable() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(JDBC_URL + DB_NAME, DB_USER, DB_PASSWORD);
            statement = connection.createStatement();
    
            String createCommentsTableSQL = "CREATE TABLE comments (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "tweet_id INT NOT NULL," +
                    "username VARCHAR(255) NOT NULL," +
                    "comment_text VARCHAR(255) DEFAULT NULL," +
                    "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (tweet_id) REFERENCES tweets(id)," +
                    "FOREIGN KEY (username) REFERENCES user(username)" +
                    ")";
            statement.executeUpdate(createCommentsTableSQL);
    
            System.out.println("Table 'comments' created successfully");
            return true;
    
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean addTweetComment(TweetComments comment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
    
        String addCommentSQL = "INSERT INTO comments (tweet_id, username, comment_text) VALUES (?, ?, ?)";
    
        try {
            connection = DriverManager.getConnection(JDBC_URL + DB_NAME, DB_USER, DB_PASSWORD);
    
            preparedStatement = connection.prepareStatement(addCommentSQL);
    
            preparedStatement.setInt(1, comment.getTweetId());
            preparedStatement.setString(2, comment.getUsername());
            preparedStatement.setString(3, comment.getCommentText());
    
            int rowsAffected = preparedStatement.executeUpdate();
    
            System.out.println("Comment added successfully.");
    
            return rowsAffected > 0;
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static ArrayList<TweetComments> getTweetComments(int tweetId) {
        ArrayList<TweetComments> comments = new ArrayList<>();
    
        try (Connection connection = DriverManager.getConnection(JDBC_URL + DB_NAME, DB_USER, DB_PASSWORD)) {
    
            String query = "SELECT * FROM comments WHERE tweet_id = ?";
    
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, tweetId);
    
                try (ResultSet results = statement.executeQuery()) {
                    while (results.next()) {
                        int commentId = results.getInt("id");
                        String username = results.getString("username");
                        String commentText = results.getString("comment_text");
                        Timestamp timestamp = results.getTimestamp("timestamp");
    
                        TweetComments comment = new TweetComments(commentId, tweetId, username, commentText, timestamp);
                        comments.add(comment);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    
        return comments;
    }
    private static boolean createReactionsTable() {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(JDBC_URL + DB_NAME, DB_USER, DB_PASSWORD);

            statement = connection.createStatement();

            String createReactionsTableSQL = "CREATE TABLE reactions (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "username VARCHAR(255) NOT NULL," +
                    "tweet_id INT NOT NULL," +
                    "reaction_type VARCHAR(20) NOT NULL," +
                    "CONSTRAINT fk_reaction_user FOREIGN KEY (username) REFERENCES user(username)," +
                    "CONSTRAINT fk_reaction_tweet FOREIGN KEY (tweet_id) REFERENCES tweets(id)," +
                    "CONSTRAINT chk_reaction_type CHECK (reaction_type IN ('like', 'love', 'smile', 'party', 'wow'))" +
                    ")";

            statement.executeUpdate(createReactionsTableSQL);

            System.out.println("Table 'reactions' created successfully");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean addReaction(Reactions reaction) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
    
        String addReactionSQL = "INSERT INTO reactions (username, tweet_id, reaction_type) VALUES (?, ?, ?)";
    
        try {
            connection = DriverManager.getConnection(JDBC_URL + DB_NAME, DB_USER, DB_PASSWORD);
    
            preparedStatement = connection.prepareStatement(addReactionSQL);
    
            preparedStatement.setString(1, reaction.getUsername());
            preparedStatement.setInt(2, reaction.getTweetId());
            preparedStatement.setString(3, reaction.getReactionType());
    
            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println("Reaction added successfully.");

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static boolean updateReaction(Reactions reaction) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
    
        String updateReactionSQL = "UPDATE reactions SET reaction_type = ? WHERE username = ? AND tweet_id = ?";
    
        try {
            connection = DriverManager.getConnection(JDBC_URL + DB_NAME, DB_USER, DB_PASSWORD);
    
            preparedStatement = connection.prepareStatement(updateReactionSQL);
    
            preparedStatement.setString(1, reaction.getReactionType());
            preparedStatement.setString(2, reaction.getUsername());
            preparedStatement.setInt(3, reaction.getTweetId());
    
            int rowsAffected = preparedStatement.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("Reaction updated successfully.");
                return true;
            } else {
                System.out.println("Failed to update reaction.");
                return false;
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public static ArrayList<Reactions> getReactionsForTweet(int tweetId) {
        ArrayList<Reactions> reactionsList = new ArrayList<>();
    
        try (Connection connection = DriverManager.getConnection(JDBC_URL + DB_NAME, DB_USER, DB_PASSWORD)) {
    
            String query = "SELECT username, tweet_id, reaction_type FROM reactions WHERE tweet_id = ?";
    
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, tweetId);
    
                try (ResultSet results = statement.executeQuery()) {
                    while (results.next()) {
                        String username = results.getString("username");
                        String reactionType = results.getString("reaction_type");
    
                        Reactions reaction = new Reactions( tweetId, username, reactionType);
                        reactionsList.add(reaction);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    
        return reactionsList;
    }
}


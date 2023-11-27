package com.cultura;
import java.sql.*;
import java.util.ArrayList;


public class CreateDB {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "cultura_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "4vPRBRYJU9.";

    public static void main(String[] args) {
        createDB();
        createUsersTable();
        createTweetsTable();
        //Tweet tweet = new Tweet("hello from user2!", "user2");
        //addTweet(tweet);
        //System.out.println(tweet);

        createFollowsTable();

        //System.out.print(getTweets());

        //System.out.println(getUserTweets("user1"));

        //new Follow("user1", "user2");

        //System.out.println(getFollowsOfUser("user1"));

        //System.out.println(getTweetsYouFollow("user1"));
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
        String insertTweetSQL = "INSERT INTO tweets (username, text) VALUES (?, ?)";

        try {
            connection = DriverManager.getConnection(JDBC_URL + DB_NAME, DB_USER, DB_PASSWORD);

            preparedStatement = connection.prepareStatement(insertTweetSQL);

            preparedStatement.setString(1, tweet.getUsername());
            preparedStatement.setString(2, tweet.getText());

            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println("Tweet added successfully.");

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

    public static ArrayList<Tweet> getTweets() {
        ArrayList<Tweet> tweets = new ArrayList<>();

        try {


            Connection connection = DriverManager.getConnection(JDBC_URL + DB_NAME, DB_USER, DB_PASSWORD);

            Statement statement = connection.createStatement();

            ResultSet results = statement.executeQuery("SELECT * FROM tweets");

            while (results.next()) {
                String text = results.getString("text");
                Timestamp timestamp = results.getTimestamp("timestamp");
                String username = results.getString("username");
                int like_count = results.getInt("likeCount");

                Tweet tweet = new Tweet(text, timestamp, username, like_count);
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
                        String text = results.getString("text");
                        Timestamp timestamp = results.getTimestamp("timestamp");
                        int like_count = results.getInt("likeCount");

                        Tweet tweet = new Tweet(text, timestamp, username, like_count);
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

    public static ArrayList<Follow> getFollowsOfUser(String username) {
        ArrayList<Follow> FollowsOfUser = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(JDBC_URL + DB_NAME, DB_USER, DB_PASSWORD);

            String getFollowsQueries = "SELECT following_username FROM follows WHERE followed_by_username = ?";

            PreparedStatement statement = connection.prepareStatement(getFollowsQueries);

            statement.setString(1, username);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                String followingUsername = results.getString("following_username");

                Follow follow = new Follow(username, followingUsername);
                FollowsOfUser.add(follow);
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
                        String text = results.getString("text");
                        Timestamp timestamp = results.getTimestamp("timestamp");
                        String tweetUsername = results.getString("username");
                        int likeCount = results.getInt("likeCount");

                        Tweet tweet = new Tweet(text, timestamp, tweetUsername, likeCount);
                        tweets.add(tweet);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return tweets;
    }


}


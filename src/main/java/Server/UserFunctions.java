package Server;

import javafx.scene.control.Alert;
import java.sql.*;

public class UserFunctions {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/cultura_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "4vPRBRYJU9.";


    public static boolean loginUser(String username, String password) {
        try {
            System.out.println("inside user login");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cultura_db", "root", "4vPRBRYJU9.");
            String query = "SELECT * FROM USER WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                System.out.println("Login successful");
                return true;
            }
            else {
                System.out.println("Login failed");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setContentText("Something went wrong with the login. Please try again.");
                alert.showAndWait();
                return false;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean signupUser(String username, String password, String name, String email){

        if (signupUserHelper(username, password, name, email)) {
            System.out.println("Signup successful!");
            return true;
        } else {
            System.out.println("Signup failed. Invalid Entries.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Signup Failed");
            alert.setContentText("Something went wrong with the signup. Please try again.");
            alert.showAndWait();
            return false;
        }
    }


    private static boolean signupUserHelper(String username, String password, String name, String email){

        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            psCheckUserExists = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();
            if (resultSet.isBeforeFirst()) {
                return false;
            }
            else {
                psInsert = connection.prepareStatement("INSERT INTO USER (username, email, name, password) values (?, ?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, email);
                psInsert.setString(3, name);
                psInsert.setString(4, password);
                psInsert.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

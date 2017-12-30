package main.java.repository;

import main.java.configuration.RepositoryConfiguration;
import main.java.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 19.11.2017.
 */
public class UserRepository {

    private Connection connection = RepositoryConfiguration.getInstance().getDbConnect();

    public boolean updateUser(User user) throws SQLException {
        User fuser = findUser(user.getUsername());
        if ( fuser != null) {
            if(fuser.getId() != user.getId()){
                return false;
            }
        }
        String query = " UPDATE user SET " +
                "username = \'" + user.getUsername() +
                "\',password = \'" + user.getPassword() +
                "\',firstname = \'" + user.getFirstName() +
                "\',lastname = \'" + user.getLastName() +
                "\',email = \'" + user.getEmail() +
                "\',role = \'" + user.getRole() +
                "\',age = \'" + user.getAge() +
                "\',adress = \'" + user.getAdress() +
                "\',gender = \'" + user.getGender() +
                "\'WHERE " +
                "id = " + user.getId() +
                ";";
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        return true;
    }

    public void addUser(User user) throws SQLException {
        if(!isUsernameFree(user.getUsername())){
            System.out.println("pls change username.");
            return;
        }

        String query = " insert into user (username, password, firstname, lastname, email, role, age, adress, gender)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString (1, user.getUsername());
        preparedStmt.setString (2, user.getPassword());
        preparedStmt.setString (3, user.getFirstName());
        preparedStmt.setString (4, user.getLastName());
        preparedStmt.setString (5, user.getEmail());
        preparedStmt.setString (6, user.getRole());
        preparedStmt.setInt (7, user.getAge());
        preparedStmt.setString (8, user.getAdress());
        preparedStmt.setString (9, user.getGender());

        preparedStmt.execute();
    }

    public boolean isUserExist(User user) throws SQLException {
        return (getAllUsers().indexOf(user) == -1) ? false : true;
    }

    public boolean isUserExist(String username, String password) throws SQLException {
        List<User> users = getAllUsers();
        for (User user : users) {
            if((user.getUsername().equals(username)) && (user.getPassword().equals(password))){
                return true;
            }
        }
        return false;
    }

    public boolean isUsernameFree(String username) throws SQLException {
        List<User> users = getAllUsers();
        for (User user : users) {
            if(user.getUsername().equals(username)){
                return false;
            }
        }
        return true;
    }

    public User findUser(String username) throws SQLException {
        List<User> users = getAllUsers();
        for (User user : users) {
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public User findUser(int id) throws SQLException {
        List<User> users = getAllUsers();
        for (User user : users) {
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from user");
        List<User> users = new ArrayList<User>();
        while (rs.next()) {
            User user = new User();

            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setFirstName(rs.getString("firstname"));
            user.setLastName(rs.getString("lastname"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getString("role"));
            user.setAge(rs.getInt("age"));
            user.setAdress(rs.getString("adress"));
            user.setGender(rs.getString("gender"));

            users.add(user);
        }

        return users;
    }

    public boolean deleteUser(int id) throws SQLException {

        User user = findUser(id);
        if(user != null){
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM user WHERE id=" + id + ";");
            return true;
        }
        return false;
    }

    public void deleteUser(User user) throws SQLException {
        if(user != null)
        deleteUser(user.getId());
    }

    public void deleteUser(String username) throws SQLException {
        User user = findUser(username);
        if(user != null){
            deleteUser(user.getId());
        }
    }
}

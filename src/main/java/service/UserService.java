package main.java.service;

import main.java.model.User;
import main.java.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 21.11.2017.
 */
public class UserService {

    private UserRepository userRepository = new UserRepository();


    public  boolean deleteUser(int id) throws SQLException {
        return userRepository.deleteUser(id);
    }

    public boolean updateUser(User user) throws SQLException {
        return userRepository.updateUser(user);
    }

    public List<User> getUsersFromRepository() throws SQLException {
        return userRepository.getAllUsers();
    }

    public boolean signIn(String userName, String password) throws SQLException {
        return (new UserRepository()).isUserExist(userName, password);
    }

    public boolean signUp(User user) throws SQLException {
        if (userRepository.isUsernameFree(user.getUsername())) {
            System.out.println("userService.sidnUp try to add user TRUE");
            userRepository.addUser(user);
            return true;
        }
        System.out.println("userService.sidnUp user exist FALSE");
        return false;
    }

    public User findUserByUserName(String userName) throws SQLException {
        return userRepository.findUser(userName);
    }

    public JSONObject convertUserToJSONobject(User user) throws JSONException {
        JSONObject object =  new JSONObject();
        object.put("id", String.valueOf(user.getId()));
        object.put("userName", user.getUsername());
        object.put("password", user.getPassword());
        object.put("firstName", user.getFirstName());
        object.put("lastName", user.getLastName());
        object.put("email", user.getEmail());
        object.put("role", user.getRole());
        object.put("adress", user.getAdress());
        object.put("gender", user.getGender());
        object.put("age", String.valueOf(user.getAge()));
        return object;
    }

    public User getUser(JSONObject object) throws JSONException {
        User user = new User();
        user.setId(Integer.parseInt((String)object.get("id")));
        user.setUsername((String)object.get("userName"));
        user.setPassword((String)object.get("password"));
        user.setFirstName((String)object.get("firstName"));
        user.setLastName((String)object.get("lastName"));
        user.setEmail((String)object.get("email"));
        user.setAge(Integer.parseInt((String)object.get("age")));
        user.setRole((String)object.get("role"));
        user.setAdress((String)object.get("adress"));
        user.setGender((String)object.get("gender"));
        return user;
    }

    public List<User> getUsersFromJSON(String string) throws JSONException {
        JSONObject object = new JSONObject(string);
        List<User> users = new ArrayList<User>();
        for (int i=0; i < (int)object.get("sizeUser"); i++){
            User user = new User();
            user.setId((int) object.get("idUser" + String.valueOf(i)));
            user.setUsername((String) object.get("userNameUser" + String.valueOf(i)));
            user.setPassword((String) object.get("passwordUser" + String.valueOf(i)));
            user.setFirstName((String) object.get("firstNameUser" + String.valueOf(i)));
            user.setLastName((String) object.get("lastNameUser" + String.valueOf(i)));
            user.setEmail((String) object.get("emailUser" + String.valueOf(i)));
            user.setRole((String) object.get("roleUser" + String.valueOf(i)));
            user.setAge((int) object.get("ageUser" + String.valueOf(i)));
            user.setAdress((String) object.get("adressUser" + String.valueOf(i)));
            user.setGender((String) object.get("genderUser" + String.valueOf(i)));
            users.add(user);
        }
        return users;
    }

    public JSONObject setUsersToJSON(List<User> users) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("sizeUser", String.valueOf(users.size()));
        for (int i=0; i < users.size(); i++){
            User user = users.get(i);
            object.put("idUser" + String.valueOf(i), String.valueOf(user.getId()));
            object.put("userNameUser" + String.valueOf(i), user.getUsername());
            object.put("passwordUser" + String.valueOf(i), user.getPassword());
            object.put("firstNameUser" + String.valueOf(i), user.getFirstName());
            object.put("lastNameUser" + String.valueOf(i), user.getLastName());
            object.put("emailUser" + String.valueOf(i), user.getEmail());
            object.put("roleUser" + String.valueOf(i), user.getRole());
            object.put("ageUser" + String.valueOf(i), String.valueOf(user.getAge()));
            object.put("adressUser" + String.valueOf(i), user.getAdress());
            object.put("genderUser" + String.valueOf(i), user.getGender());
        }
        return object;
    }


}

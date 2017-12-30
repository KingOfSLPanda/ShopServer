package main.java.controller;

import main.java.model.*;
import main.java.repository.ProductRepository;
import main.java.service.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by User on 20.11.2017.
 */
public class MainController {

    private MessageTransmissionService messageTransmissionService;

    public MainController(MessageTransmissionService messageTransmissionService){
        this.messageTransmissionService = messageTransmissionService;
    }

    public void loginMenu() throws IOException, JSONException, ClassNotFoundException, SQLException, MessagingException {
        boolean check = true;
        UserService userService = new UserService();
        User user = new User();
        Category category = new Category();
        Order order = new Order();
        JSONObject object = new JSONObject();
        while (check){
            JSONObject message = this.messageTransmissionService.getMessage();
            String result;
            switch ((String)message.get("action")){
                case "Hi":
                    System.out.println("Hi!");
                    this.messageTransmissionService.sendMessage("request", "Zdarova");
                    break;
                case "addProduct":
                    System.out.println("add product");
                    ProductService.addProduct(ProductService.getProductsFromJSONobject(message.toString()).get(0));
                    this.messageTransmissionService.sendMessage("request", "true");
                    break;
                case "updateProduct":
                    System.out.println("update product");
                    ProductService.updateProduct(ProductService.getProductsFromJSONobject(message.toString()).get(0));
                    this.messageTransmissionService.sendMessage("request", "true");
                    break;
                case  "deleteProduct":
                    System.out.println("delete product");
                    (new ProductRepository()).deleteProduct(Integer.parseInt((String) message.get("idProduct")));
                    this.messageTransmissionService.sendMessage("request", "true");
                    break;
                case "signIn":
                    System.out.println("try to sign in!");
                    result = String.valueOf(userService.signIn((String)message.get("userName"), (String)message.get("password")));
                    user = userService.findUserByUserName((String)message.get("userName"));
                    if (user != null) {
                        object = userService.convertUserToJSONobject(user);
                    }
                    object.put("request", result);
                    this.messageTransmissionService.sendMessage(object);
                    break;
                case "signUp":
                    System.out.println("registration");
                    result = String.valueOf(userService.signUp(userService.getUser(message)));
                    this.messageTransmissionService.sendMessage("request", result);
                    break;
                case "getUsers":
                    System.out.println("get catigories!");
                    result = userService.setUsersToJSON(userService.getUsersFromRepository()).toString();
                    this.messageTransmissionService.sendMessage("request", result);
                    break;
                case "deleteUser":
                    result = String.valueOf(userService.deleteUser(Integer.parseInt((String)message.get("idUser"))));
                    this.messageTransmissionService.sendMessage("request", result);
                    break;
                case "addCategory":
                    System.out.println("try to add catigory!");
                    category = CategoryService.getListOfCategoriesFromJSONobject(message.toString()).get(0);
                    result = String.valueOf(CategoryService.addCategory(category));
                    this.messageTransmissionService.sendMessage("request", result);
                    break;
                case "updateCategory":
                    System.out.println("try to update catigory!");
                    category = CategoryService.getListOfCategoriesFromJSONobject(message.toString()).get(0);
                    result = String.valueOf(CategoryService.updateCategory(category));
                    this.messageTransmissionService.sendMessage("request", result);
                    break;
                case "deleteCategory":
                    System.out.println("try to delete catigory!");
                    category = CategoryService.getListOfCategoriesFromJSONobject(message.toString()).get(0);
                    result = String.valueOf(CategoryService.deleteCategory(category.getId()));
                    this.messageTransmissionService.sendMessage("request", result);
                    break;
                case "getCategories":
                    System.out.println("get catigories!");
                    result = CategoryService.convertListOfCategoriesToJSONobject(CategoryService.getCatigories()).toString();
                    this.messageTransmissionService.sendMessage("request", result);
                    break;
                case "getProducts":
                    System.out.println("get products!");
                    result = ProductService.convertProductsToJSONobject(ProductService.getAllProducts()).toString();
                    this.messageTransmissionService.sendMessage("request", result);
                    break;
                case "getCategoriesAndProducts": //don't work
                    System.out.println("get categories and products!");
                    result = ProductService.convertProductsToJSONobject(ProductService.getAllProducts()).toString();
                    this.messageTransmissionService.sendMessage("request", result);
                    break;
                case "getProductsByCategoryId":
                    System.out.println("get products by category id!");
                    int categoryId = (int) message.get("categoryId");
                    result = ProductService.convertProductsToJSONobject(ProductService.getProductsByCategoryId(categoryId)).toString();
                    this.messageTransmissionService.sendMessage("request", result);
                    break;
                case "updateUser":
                    System.out.println("try to updateUser!");
                    user = userService.getUser(message);
                    result = String.valueOf(userService.updateUser(user));
                    this.messageTransmissionService.sendMessage("request", result);
                    break;
                case "addOrder":
                    System.out.println("try to add order!");
                    order = OrderService.getListOfOrders(message.toString()).get(0);
                    result = String.valueOf(OrderService.addOrder(order));
                    this.messageTransmissionService.sendMessage("request", result);
                    break;
                case "getOrders":
                    System.out.println("get orders!");
                    result = OrderService.convertListOfOrdersToJSON(OrderService.getOrders()).toString();
                    this.messageTransmissionService.sendMessage("request", result);
                    break;
                case "getDescriptionOrders":
                    System.out.println("get description orders!");
                    result = OrderService.convertDescriptionOrdersToJSON(OrderService.getDescriptionOrders()).toString();
                    this.messageTransmissionService.sendMessage("request", result);
                    break;
                case "acceptOrder":
                    System.out.println("accept order!");
                    result = String.valueOf(OrderService.acceptOrder((int) message.get("idOrder")));
                    this.messageTransmissionService.sendMessage("request", result);
                    break;
                case "cancleOrder":
                    System.out.println("cancle order!");
                    result = String.valueOf(OrderService.cancleOrder((int) message.get("idOrder")));
                    this.messageTransmissionService.sendMessage("request", result);
                    break;
                case "sendShares":
                    System.out.println("shares!");
                    result = String.valueOf(MailService.sendShares());
                    this.messageTransmissionService.sendMessage("request", result);
                    break;
                case "writeReport":
                    System.out.println("report!");
                    ProductService.writeReport();
                    break;
            }
        }

    }
}
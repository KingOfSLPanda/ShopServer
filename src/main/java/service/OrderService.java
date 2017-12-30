package main.java.service;

import main.java.model.Category;
import main.java.model.DescriptionOrder;
import main.java.model.Order;
import main.java.model.Product;
import main.java.repository.CategoryRepository;
import main.java.repository.OrderRepository;
import main.java.repository.ProductRepository;
import org.json.JSONException;
import org.json.JSONObject;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 04.12.2017.
 */
public class OrderService {

    public static boolean acceptOrder(int id) throws SQLException, IOException, MessagingException {
        MailService.send((new OrderRepository()).findUserEmail(id), "Ваш заказ принят.");
        return (new OrderRepository()).deleteOrder(id);
    }

    public static boolean cancleOrder(int id) throws SQLException, IOException, MessagingException {
        MailService.send((new OrderRepository()).findUserEmail(id), "Выш заказ отклонен.");
        return (new OrderRepository()).deleteOrder(id);
    }

    public static boolean addOrder(Order order) throws SQLException {
        if ((new OrderRepository()).addOrder(order)){
            Product product = (new ProductRepository()).findProduct(order.getProductId());
            Category category = (new CategoryRepository()).findCategory(product.getCategoryId());
            category.setNumberOfOrders(category.getNumberOfOrders() + 1);
            CategoryService.updateCategory(category);
            return true;
        }
        return false;
    }

    public static List<Order> getOrders() throws SQLException {
        return (new OrderRepository()).getAllOrders();
    }

    public static List<DescriptionOrder> getDescriptionOrders() throws SQLException {
        return  (new OrderRepository()).getDescriptionOrders();
    }

    public static List<DescriptionOrder> getListOfDescriptionOrders(String string) throws JSONException {
        JSONObject object = new JSONObject(string);
        List<DescriptionOrder> orders = new ArrayList<DescriptionOrder>();
        for (int i = 0; i < (int) object.get("sizeDescriptionOrder"); i++){
            DescriptionOrder order = new DescriptionOrder();
            order.setId((int) object.get("idDescriptionOrder" + String.valueOf(i)));
            order.setUserName((String) object.get("userNameDescriptionOrder" + String.valueOf(i)));
            order.setProductName((String) object.get("productNameDescriptionOrder" + String.valueOf(i)));
            orders.add(order);
        }
        return orders;
    }

    public static JSONObject convertDescriptionOrdersToJSON(List<DescriptionOrder> orders) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("sizeDescriptionOrder", orders.size());
        for (int i = 0; i < orders.size(); i++){
            object.put("idDescriptionOrder" + String.valueOf(i), orders.get(i).getId());
            object.put("userNameDescriptionOrder" + String.valueOf(i), orders.get(i).getUserName());
            object.put("productNameDescriptionOrder" + String.valueOf(i), orders.get(i).getProductName());
        }
        return object;
    }

    public static List<Order> getListOfOrders(String string) throws JSONException {
        JSONObject object = new JSONObject(string);
        List<Order> orders = new ArrayList<Order>();
        for (int i = 0; i < (int) object.get("sizeOrder"); i++){
            Order order = new Order();
            order.setId((int) object.get("idOrder" + String.valueOf(i)));
            order.setUserId((int) object.get("userIdOrder" + String.valueOf(i)));
            order.setProductId((int) object.get("productIdOrder" + String.valueOf(i)));
            orders.add(order);
        }
        return orders;
    }

    public static JSONObject convertListOfOrdersToJSON(List<Order> orders) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("sizeOrder", orders.size());
        for (int i = 0; i < orders.size(); i++){
            object.put("idOrder" + String.valueOf(i), orders.get(i).getId());
            object.put("userIdOrder" + String.valueOf(i), orders.get(i).getUserId());
            object.put("productIdOrder" + String.valueOf(i), orders.get(i).getProductId());
        }
        return object;
    }

}

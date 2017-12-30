package main.java.repository;

import main.java.configuration.RepositoryConfiguration;
import main.java.model.DescriptionOrder;
import main.java.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 04.12.2017.
 */
public class OrderRepository {

    private Connection connection = RepositoryConfiguration.getInstance().getDbConnect();

    public boolean addOrder(Order order){
        String query = " insert into orders (userId, productId)"
                + " values (?, ?)";
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString (1, String.valueOf(order.getUserId()));
            preparedStmt.setString (2, String.valueOf(order.getProductId()));
            preparedStmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return  true;
    }

    public boolean deleteOrder(int id) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM orders WHERE id=" + id + ";");
        return true;
    }

    public List<DescriptionOrder> getDescriptionOrders() throws SQLException {
        Statement statement = connection.createStatement();
        List<DescriptionOrder> descriptionOrders = new ArrayList<DescriptionOrder>();
        ResultSet rs = statement.executeQuery(" select * " +
                "from orders " +
                "inner join user " +
                "on orders.userId=user.id " +
                "inner join product " +
                "on orders.productId=product.id;");
        while (rs.next()){
            DescriptionOrder order = new DescriptionOrder();
            order.setId(rs.getInt("id"));
            order.setUserName(rs.getString("username"));
            order.setProductName(rs.getString("title"));
            descriptionOrders.add(order);
        }
        return descriptionOrders;
    }

    public String findUserEmail(int orderId) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(" select email " +
                "from orders " +
                "inner join user " +
                "on orders.userId=user.id " +
                "where orders.id=" + orderId + ";");
        while (rs.next()){
            return rs.getString("email");
        }
        return "";
    }

    public List<Order> getAllOrders() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from orders");
        List<Order> orders = new ArrayList<Order>();
        while (rs.next()){
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setUserId(rs.getInt("userId"));
            order.setProductId(rs.getInt("productId"));
            orders.add(order);
        }
        return orders;
    }
}

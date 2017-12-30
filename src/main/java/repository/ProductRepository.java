package main.java.repository;

import main.java.configuration.RepositoryConfiguration;
import main.java.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by User on 25.11.2017.
 */
public class ProductRepository {

    private Connection connection = RepositoryConfiguration.getInstance().getDbConnect();

    public void updateProduct(Product product) throws SQLException {
        if (findProduct(product.getId()) == null) {
            return;
        }
        String query = " UPDATE product SET " +
//                "img = \'" + product.getImg() +
                "title = \'" + product.getTitle() +
                "\',material = \'" + product.getMaterial() +
                "\',height = \'" + product.getHeight() +
                "\',width = \'" + product.getWidth() +
                "\',length = \'" + product.getLength() +
                "\',weight = \'" + product.getWeight() +
                "\',description = \'" + product.getDescription() +
                "\',count = \'" + product.getCount() +
                "\',cost = \'" + product.getCost() +
                "\',category_id = \'" + product.getCategoryId() +
                "\'WHERE " +
                "id = " + product.getId() +
                ";";
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public List<Product> getAllProducts() throws SQLException {
        System.out.println("get products! ProductRepository");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from product");
        List<Product> products = new ArrayList<Product>();
        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setImg(rs.getString("img"));
            product.setTitle(rs.getString("title"));
            product.setMaterial(rs.getString("material"));
            product.setHeight(rs.getInt("height"));
            product.setWidth(rs.getInt("width"));
            product.setLength(rs.getInt("length"));
            product.setWeight(rs.getInt("weight"));
            product.setDescription(rs.getString("description"));
            product.setCount(rs.getInt("count"));
            product.setCost(rs.getInt("cost"));
            product.setCategoryId(rs.getInt("category_id"));
            products.add(product);
        }
        return products;
    }

    public Product findProduct(int id) throws SQLException {
        List<Product> products = getAllProducts();
        for (Product product : products) {
            if(product.getId() == id){
                return product;
            }
        }
        return null;
    }

    public Product findProduct(String title) throws SQLException {
        List<Product> products = getAllProducts();
        for (Product product : products) {
            if(product.getTitle().equals(title)){
                return product;
            }
        }
        return null;
    }

    public Product findProduct(Product fProduct) throws SQLException {
        List<Product> products = getAllProducts();
        for (Product product : products) {
            if(product.getTitle().equals(fProduct)){
                return product;
            }
        }
        return null;
    }

    public List<Product> findProductsByCategoryId(int id) throws SQLException {
        List<Product> products = getAllProducts();
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getCategoryId() != id){
                products.remove(i);
                i--;
            }
        }
        return products;
    }

    public void addProduct(Product product) throws SQLException {
        String query = " insert into product (" +
                "img, title, material, height, width, length, weight, description, count, cost, category_id)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString (1, product.getImg());
        preparedStmt.setString (2, product.getTitle());
        preparedStmt.setString (3, product.getMaterial());
        preparedStmt.setInt (4, product.getHeight());
        preparedStmt.setInt (5, product.getWidth());
        preparedStmt.setInt (6, product.getLength());
        preparedStmt.setInt (7, product.getWeight());
        preparedStmt.setString (8, product.getDescription());
        preparedStmt.setInt (9, product.getCount());
        preparedStmt.setInt (10, product.getCost());
        preparedStmt.setInt (11, product.getCategoryId());
        preparedStmt.execute();
    }

    public void deleteProduct(Product product) throws SQLException {
        product = findProduct(product);
        if(product != null){
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM product WHERE id=" + product.getId() + ";");
        }
    }

    public void deleteProduct(int id) throws SQLException {
        Product product = findProduct(id);
        if(product != null){
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM product WHERE id=" + product.getId() + ";");
        }
    }

}

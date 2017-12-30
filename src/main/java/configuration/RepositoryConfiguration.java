package main.java.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by User on 15.11.2017.
 */
public class RepositoryConfiguration {

    private Connection dbConnect;

    private static RepositoryConfiguration ourInstance = new RepositoryConfiguration();

    public static RepositoryConfiguration getInstance() {
        return ourInstance;
    }

    private RepositoryConfiguration() {
    }

    public void connection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/shop?useSSL=false";
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        this.dbConnect = DriverManager.getConnection(url, createDBProperties(new Properties()));
    }

    private Properties createDBProperties(Properties properties){
        properties.put("user", "root");
        properties.put("password", "root");
        properties.put("autoReconnect", "true");
        properties.put("characterEncoding", "UTF-8");
        properties.put("useUnicode", "true");
        return properties;
    }

    public void createDBUserTable() throws SQLException {
        Statement statement = this.dbConnect.createStatement();
        String createTableSQL ="CREATE TABLE IF NOT EXISTS User (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "username VARCHAR(30) NOT NULL, " +
                "password VARCHAR(30) NOT NULL, " +
                "firstname VARCHAR(30) , " +
                "lastname VARCHAR(30) , " +
                "email VARCHAR(30) NOT NULL, " +
                "role VARCHAR(30) NOT NULL, " +
                "age int , " +
                "adress VARCHAR(30), " +
                "gender VARCHAR(30), " +
                "PRIMARY KEY (id)" +
                ")";
        statement.executeUpdate(createTableSQL);
    }

    public void createDBCategoriesTable() throws SQLException {
        Statement statement = this.dbConnect.createStatement();
        String createTableSQL ="CREATE TABLE IF NOT EXISTS categories (" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "categoryName VARCHAR(30) NOT NULL, " +
                "numberOfOrders INT NOT NULL )";
        statement.executeUpdate(createTableSQL);
    }

    public void createDBProductTable() throws SQLException {
        Statement statement = this.dbConnect.createStatement();
        String createTableSQL ="CREATE TABLE IF NOT EXISTS product (" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "img VARCHAR(255) NOT NULL, " +
                "title VARCHAR(30) NOT NULL, " +
                "material VARCHAR(30) NOT NULL, " +
                "height INT NOT NULL, " +
                "width INT NOT NULL, " +
                "length INT NOT NULL, " +
                "weight INT NOT NULL, " +
                "description VARCHAR(255) , " +
                "count INT NOT NULL, " +
                "cost INT NOT NULL, " +
                "category_id INT NOT NULL, " +
                "FOREIGN KEY (category_id) REFERENCES categories (id)" +
                ")";
        statement.executeUpdate(createTableSQL);
    }

    public void createDBOrdersTable() throws SQLException {
        Statement statement = this.dbConnect.createStatement();
        String createTableSQL ="CREATE TABLE IF NOT EXISTS orders (" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "userId INT NOT NULL, " +
                "productId INT NOT NULL, " +
                "FOREIGN KEY (productId) REFERENCES product (id)" +
                ")";
        statement.executeUpdate(createTableSQL);
    }

    public Connection getDbConnect() {
        return dbConnect;
    }

    public void setDbConnect(Connection dbConnect) {
        this.dbConnect = dbConnect;
    }
}
package main.java.service;

import main.java.model.Product;
import main.java.repository.ProductRepository;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 26.11.2017.
 */
public class ProductService {

    public static void deleteProduct(int id) throws SQLException {
        (new ProductRepository()).deleteProduct(id);
    }

    public static List<Product> getProductsByCategoryId(int id) throws SQLException {
        System.out.println("get products bu category id! ProductService");
        return (new ProductRepository()).findProductsByCategoryId(id);
    }

    public static void addProduct(Product product) throws SQLException {
        (new ProductRepository()).addProduct(product);
    }

    public static void updateProduct(Product product) throws SQLException {
        (new ProductRepository()).updateProduct(product);
    }

    public static List<Product> getAllProducts() throws SQLException {
        System.out.println("get products! ProductService");
        return (new ProductRepository()).getAllProducts();
    }

    public static JSONObject convertProductsToJSONobject(List<Product> products) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("sizeProduct", products.size());
        for (int i=0; i<products.size(); i++){
            object.put("idProduct" + String.valueOf(i), products.get(i).getId());
            object.put("imgProduct" + String.valueOf(i), products.get(i).getImg());
            object.put("titleProduct" + String.valueOf(i), products.get(i).getTitle());
            object.put("materialProduct" + String.valueOf(i), products.get(i).getMaterial());
            object.put("heightProduct" + String.valueOf(i), products.get(i).getHeight());
            object.put("widthProduct" + String.valueOf(i), products.get(i).getWidth());
            object.put("lengthProduct" + String.valueOf(i), products.get(i).getLength());
            object.put("weightProduct" + String.valueOf(i), products.get(i).getWeight());
            object.put("descriptionProduct" + String.valueOf(i), products.get(i).getDescription());
            object.put("countProduct" + String.valueOf(i), products.get(i).getCount());
            object.put("costProduct" + String.valueOf(i), products.get(i).getCost());
            object.put("categoryIdProduct" + String.valueOf(i), products.get(i).getCategoryId());
        }
        return object;
    }

    public static List<Product> getProductsFromJSONobject(String string) throws JSONException {
        JSONObject object = new JSONObject(string);
        List<Product> products = new ArrayList<Product>();
        for(int i = 0; i < (int)object.get("sizeProduct"); i++){
            Product product = new Product();
            product.setId((int) object.get("idProduct" + String.valueOf(i)));
            product.setImg((String) object.get("imgProduct" + String.valueOf(i)));
            product.setTitle((String) object.get("titleProduct" + String.valueOf(i)));
            product.setMaterial((String) object.get("materialProduct" + String.valueOf(i)));
            product.setHeight((int) object.get("heightProduct" + String.valueOf(i)));
            product.setWidth((int) object.get("widthProduct" + String.valueOf(i)));
            product.setLength((int) object.get("lengthProduct" + String.valueOf(i)));
            product.setWeight((int) object.get("weightProduct" + String.valueOf(i)));
            product.setDescription((String) object.get("descriptionProduct" + String.valueOf(i)));
            product.setCount((int) object.get("countProduct" + String.valueOf(i)));
            product.setCost((int) object.get("costProduct" + String.valueOf(i)));
            product.setCategoryId((int) object.get("categoryIdProduct" + String.valueOf(i)));

            products.add(product);
        }
        return  products;
    }

    public static void writeReport() throws SQLException, IOException {
        List<Product> products = (new ProductRepository()).getAllProducts();
        FileWriter writer = new FileWriter("products.txt");
        for(Product product : products) {
            String title = product.getTitle();
            String cost = String.valueOf(product.getCost());
            String count = String.valueOf(product.getCount());
            writer.write(title + " " + cost + " " + count + System.getProperty("line.separator"));
        }
        writer.close();
    }

}

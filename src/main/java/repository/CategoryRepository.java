package main.java.repository;

import main.java.configuration.RepositoryConfiguration;
import main.java.model.Category;
import main.java.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 25.11.2017.
 */
public class CategoryRepository {

    private Connection connection = RepositoryConfiguration.getInstance().getDbConnect();

    public List<Category> getAllCatigories() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from categories");
        List<Category> categories = new ArrayList<Category>();
        while (rs.next()) {
            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setCategoryName(rs.getString("categoryName"));
            category.setNumberOfOrders(rs.getInt("numberOfOrders"));

            categories.add(category);
        }

        return categories;
    }

    public Category findCategory(int id) throws SQLException {
        List<Category> categories = getAllCatigories();
        for (Category category : categories) {
            if(category.getId() == id){
                return category;
            }
        }
        return null;
    }

    public Category findCategory(String categoryName) throws SQLException {
        List<Category> categories = getAllCatigories();
        for (Category category : categories) {
            if(category.getCategoryName().equals(categoryName)){
                return category;
            }
        }
        return null;
    }

    public boolean isCategoryExist(String categoryName) throws SQLException {
        List<Category> categories = getAllCatigories();
        for (Category category : categories) {
            if(category.getCategoryName().equals(categoryName)){
                return true;
            }
        }
        return false;
    }

    public boolean addCategory(Category category) throws SQLException {
        if(isCategoryExist(category.getCategoryName())){
            System.out.println("pls change categoryName.");
            return false;
        }

        String query = " insert into categories (categoryName, numberOfOrders)"
                + " values (?, ?)";

        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString (1, category.getCategoryName());
        preparedStmt.setString (2, String.valueOf(category.getNumberOfOrders()));
        preparedStmt.execute();
        return  true;
    }

    public boolean updateCategory(Category category) throws SQLException {
        Category fcategory = findCategory(category.getCategoryName());
        if(fcategory != null){
            if (fcategory.getId() != category.getId()){
                return  false;
            }
        }
        String query = " UPDATE categories SET " +
                "categoryName = \'" + category.getCategoryName() +
                "\', numberOfOrders = \'" + category.getNumberOfOrders() +
                "\'WHERE " +
                "id = " + category.getId() +
                ";";
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        return true;

    }

    public boolean deleteCategory(int id) throws SQLException {
        Category category = findCategory(id);
        if(category != null){
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM categories WHERE id=" + id + ";");
            return true;
        }
        return false;
    }

    public void deleteCategory(String categoryName) throws SQLException {
        Category category = findCategory(categoryName);
        if(category != null){
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM categories WHERE categoryNmae=" + categoryName + ";");
        }
    }

}
